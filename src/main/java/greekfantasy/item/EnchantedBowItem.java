package greekfantasy.item;

import greekfantasy.GreekFantasy;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class EnchantedBowItem extends BowItem {

    public EnchantedBowItem(Properties builder) {
        super(builder);
    }

    /**
     * Copy of BowItem#releaseUsing with hooks for custom behavior
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            // In 1.21, enchantments are data-driven
            boolean doNotConsume = player.getAbilities().instabuild || stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY)) > 0;
            ItemStack ammo = player.getProjectile(stack);

            int useDuration = this.getUseDuration(stack, entityLiving) - timeLeft;
            useDuration = net.neoforged.neoforge.event.EventHooks.onArrowLoose(stack, level, player, useDuration, !ammo.isEmpty() || doNotConsume);
            if (useDuration < 0) {
                return;
            }

            if (!ammo.isEmpty() || doNotConsume) {
                if (ammo.isEmpty()) {
                    ammo = new ItemStack(Items.ARROW);
                }

                float power = getPowerForTime(useDuration);
                if (!(power < 0.1D)) {
                    boolean isInfinite = player.getAbilities().instabuild
                            || (ammo.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(ammo, stack, player));
                    if (!level.isClientSide()) {
                        // shoot first arrow, then shoot remaining arrows (if any) with adjusted stats
                        shootArrow(level, stack, ammo, power, 1.0F, isInfinite, player);
                        for (int i = 1, arrowCount = getArrowCount(stack); i < arrowCount; i++) {
                            shootArrow(level, stack, ammo, power, 8.0F, true, player);
                        }
                        // attempt to damage item
                        int damage = getDamageOnUse(stack);
                        if(damage > 0) {
                            stack.hurtAndBreak(damage, player, player.getUsedItemHand() == net.minecraft.world.InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
                        }
                    }
                    // play sound
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);
                    // shrink item stack if applicable
                    if (!isInfinite && !player.getAbilities().instabuild) {
                        ammo.shrink(1);
                        if (ammo.isEmpty()) {
                            player.getInventory().removeItem(ammo);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Creates and shoots an arrow
     * @param level the level
     * @param itemStack the bow
     * @param ammo the ammo
     * @param power the power factor
     * @param inaccuracy the innacuracy factor
     * @param creativePickup true if the arrow has creative-only pickup status
     * @param player the player that is shooting the arrow
     * @return the arrow after it has been added to the world
     */
    protected AbstractArrow shootArrow(final Level level, final ItemStack itemStack, final ItemStack ammo,
                                       final float power, final float inaccuracy, final boolean creativePickup,
                                       final Player player) {
        // create arrow entity
        ArrowItem arrowitem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
        AbstractArrow arrow = arrowitem.createArrow(level, ammo, player, itemStack);
        // In 1.21, customArrow takes (arrow, bowStack, arrowStack)
        arrow = customArrow(arrow, itemStack, ammo);
        // params: thrower, rotationPitch, rotationYaw, ???, speed, inaccuracy
        float velocity = power * 3.0F * getArrowVelocityMultiplier();
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, inaccuracy);
        // set crit
        if (power >= 1.0F) {
            arrow.setCritArrow(true);
        }
        // apply power enchantment - In 1.21, enchantments are data-driven
        int powerEnchant = itemStack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER));
        if (powerEnchant > 0) {
            arrow.setBaseDamage(arrow.getBaseDamage() + (double) powerEnchant * 0.5D + 0.5D);
        }
        // apply punch enchantment
        int punchEnchant = itemStack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PUNCH));
        // 1.21: setKnockback removed - knockback is now handled via weapon enchantments automatically
        // if (punchEnchant > 0) {
        //     arrow.setKnockback(punchEnchant);
        // }
        // apply flame enchantment
        if (itemStack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME)) > 0) {
            arrow.igniteForSeconds(100);
        }
        // set pickup status
        if (creativePickup || player.getAbilities().instabuild && (ammo.is(Items.SPECTRAL_ARROW) || ammo.is(Items.TIPPED_ARROW))) {
            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        // actually add the arrow entity
        level.addFreshEntity(arrow);
        return arrow;
    }

    protected abstract int getBaseEnchantmentCount();

    protected float getArrowVelocityMultiplier() {
        return 1.0F;
    }

    protected int getArrowCount(final ItemStack stack) {
        return 1;
    }

    protected int getDamageOnUse(final ItemStack stack) {
        return 1;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player playerIn) {
        checkAndApplyBaseEnchantments(stack, level);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Note: getEnchantmentTags() removed in 1.21, use isEnchanted()
        return stack.isEnchanted();
    }

    @Override
    public void inventoryTick(final ItemStack stack, final Level level, final Entity entityIn,
                              final int itemSlot, final boolean isSelected) {
        checkAndApplyBaseEnchantments(stack, level);
    }

    public abstract void checkAndApplyBaseEnchantments(final ItemStack stack, final Level level);

    public static class AvernalBowItem extends EnchantedBowItem {
        public AvernalBowItem(Properties builder) {
            super(builder);
        }

        @Override
        public void checkAndApplyBaseEnchantments(final ItemStack stack, final Level level) {
            // In 1.21, enchantments are data-driven
            if (stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME)) < 1) {
                greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, Enchantments.FLAME, 1);
            }
            if (stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.VANISHING_CURSE)) < 1) {
                greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, Enchantments.VANISHING_CURSE, 1);
            }
        }

        @Override
        protected int getBaseEnchantmentCount() {
            return 2;
        }

        @Override
        protected float getArrowVelocityMultiplier() {
            return 1.0F;
        }

        @Override
        protected int getDamageOnUse(ItemStack stack) {
            return GreekFantasy.CONFIG.AVERNAL_BOW_DURABILITY_ON_USE.get();
        }
    }

    public static class ArtemisBowItem extends EnchantedBowItem {
        public ArtemisBowItem(Properties builder) {
            super(builder);
        }

        @Override
        public void checkAndApplyBaseEnchantments(final ItemStack stack, final Level level) {
            // In 1.21, enchantments are data-driven
            if (stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER)) < 5) {
                greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, Enchantments.POWER, 5);
            }
        }

        @Override
        protected int getBaseEnchantmentCount() {
            return 1;
        }

        @Override
        protected float getArrowVelocityMultiplier() {
            return 1.25F;
        }

        @Override
        protected int getArrowCount(final ItemStack stack) {
            return 3;
        }

        @Override
        protected int getDamageOnUse(ItemStack stack) {
            return GreekFantasy.CONFIG.ARTEMIS_BOW_DURABILITY_ON_USE.get();
        }

        @Override
        public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
            arrow.setBaseDamage(arrow.getBaseDamage() * 1.25D);
            return arrow;
        }

        @Override
        public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
            // add multishot tooltip
            list.add(Component.translatable("enchantment.minecraft.multishot").withStyle(ChatFormatting.AQUA)
                    .append(" ").append(Component.translatable("enchantment.level.2").withStyle(ChatFormatting.AQUA)));
        }
    }

    public static class ApolloBowItem extends EnchantedBowItem {
        public ApolloBowItem(Properties builder) {
            super(builder);
        }

        @Override
        public void checkAndApplyBaseEnchantments(final ItemStack stack, final Level level) {
            // In 1.21, enchantments are data-driven
            if (stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME)) < 1) {
                greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, Enchantments.FLAME, 1);
            }
        }

        @Override
        protected int getBaseEnchantmentCount() {
            return 1;
        }

        @Override
        protected float getArrowVelocityMultiplier() {
            return 1.5F;
        }

        @Override
        protected int getArrowCount(final ItemStack stack) {
            return 2;
        }

        @Override
        protected int getDamageOnUse(ItemStack stack) {
            return GreekFantasy.CONFIG.APOLLO_BOW_DURABILITY_ON_USE.get();
        }

        @Override
        public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
            arrow.setBaseDamage(arrow.getBaseDamage() * 1.75D);
            return arrow;
        }

        @Override
        public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
            // add multishot tooltip
            list.add(Component.translatable("enchantment.minecraft.multishot").withStyle(ChatFormatting.AQUA)
                    .append(" ").append(Component.translatable("enchantment.level.1").withStyle(ChatFormatting.AQUA)));
        }
    }
}
