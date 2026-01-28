package greekfantasy.item;

import com.google.common.collect.ImmutableMultimap;
import greekfantasy.GreekFantasy;
import greekfantasy.entity.misc.Spear;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
// TODO 1.21: Vanishable interface removed - use vanishing_curse enchantment tag instead
// import net.minecraft.world.item.Vanishable;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;

public class SpearItem extends TieredItem {

    public static final ResourceLocation ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "spear_attack_damage");
    public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "spear_attack_speed");
    public static final ResourceLocation ATTACK_RANGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "spear_attack_range");
    public static final String KEY_MOB_EFFECT = "Potion";

    protected Multimap<Holder<Attribute>, AttributeModifier> spearAttributes;
    protected final float attackRange;
    protected final int setFire;

    public SpearItem(Tier tier, Item.Properties properties) {
        this(tier, 0.25F, properties);
    }

    public SpearItem(Tier tier, float attackRange, Item.Properties properties) {
        this(tier, attackRange, properties, 0);
    }

    public SpearItem(Tier tier, float attackRange, Item.Properties properties, int setFire) {
        super(tier, properties);
        this.attackRange = attackRange;
        this.setFire = setFire;
    }

    @Override
    public boolean canAttackBlock(final BlockState state, final Level world, final BlockPos pos, final Player player) {
        return !player.isCreative();
    }

    @Override
    public UseAnim getUseAnimation(final ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(final ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean isFoil(final ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return super.isFoil(stack) || (!customData.isEmpty() && customData.copyTag().contains(KEY_MOB_EFFECT));
    }

    @Override
    public void releaseUsing(final ItemStack stack, final Level world, final LivingEntity entity, final int duration) {
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;

        int useDuration = getUseDuration(stack, entity) - duration;
        if (useDuration < 10) {
            return;
        }

        if (!world.isClientSide()) {
            throwSpear(world, player, stack);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    protected void throwSpear(final Level world, final Player thrower, final ItemStack stack) {
        stack.hurtAndBreak(1, thrower, thrower.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        Spear spear = new Spear(world, thrower, stack, setFire);
        spear.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, 2.25F, 1.0F);
        // set pickup status and remove the itemstack
        if (thrower.getAbilities().instabuild) {
            spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        } else {
            thrower.getInventory().removeItem(stack);
        }
        world.addFreshEntity(spear);
        world.playSound(null, spear, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(final Level world, final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean hurtEnemy(final ItemStack stack, final LivingEntity target, final LivingEntity user) {
        stack.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!customData.isEmpty() && customData.copyTag().contains(SpearItem.KEY_MOB_EFFECT)) {
            final CompoundTag nbt = customData.copyTag().getCompound(SpearItem.KEY_MOB_EFFECT).copy();
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.getOptional(ResourceLocation.parse(nbt.getString(SpearItem.KEY_MOB_EFFECT))).orElse(null);
            if (effect != null) {
                nbt.putByte("Id", (byte) BuiltInRegistries.MOB_EFFECT.getId(effect));
                MobEffectInstance effectInstance = MobEffectInstance.load(nbt);
                if(effectInstance != null) {
                    target.addEffect(effectInstance);
                }
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(final ItemStack stack, final Level world, final BlockState state,
                             final BlockPos pos, final LivingEntity entity) {
        if (state.getDestroySpeed(world, pos) != 0.0D) {
            stack.hurtAndBreak(2, entity, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers should be set via Item.Properties.attributes() in registration
    // The attack range modifier needs a different approach in 1.21

    // TODO 1.21: canApplyAtEnchantingTable removed - use enchantment tags in data/greekfantasy/tags/item/enchantable/ instead
    // Spears can have Loyalty enchantment via data-driven system

    @Override
    public int getEnchantmentValue() {
        return Math.max(1, super.getEnchantmentValue() / 2);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!customData.isEmpty() && customData.copyTag().contains(SpearItem.KEY_MOB_EFFECT)) {
            final CompoundTag nbt = customData.copyTag().getCompound(SpearItem.KEY_MOB_EFFECT).copy();
            MobEffect potion = BuiltInRegistries.MOB_EFFECT.getOptional(ResourceLocation.parse(nbt.getString(KEY_MOB_EFFECT))).orElse(null);
            if (potion != null) {
                int level = 1 + nbt.getInt("Amplifier");
                tooltip.add(Component.translatable(potion.getDescriptionId()).append(" ")
                        .append(Component.translatable("enchantment.level." + level))
                        .withStyle(ChatFormatting.GREEN));
            }
        }
    }

    // 1.21: initializeClient removed - use RegisterClientExtensionsEvent in GFClientEvents instead
}
