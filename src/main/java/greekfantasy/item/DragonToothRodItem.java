package greekfantasy.item;

import greekfantasy.entity.misc.DragonToothHook;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class DragonToothRodItem extends FishingRodItem {

    public static final net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> FISHING_LUCK = net.minecraft.world.item.enchantment.Enchantments.LUCK_OF_THE_SEA;
    public static final int FISHING_LUCK_LEVEL = 3;

    public DragonToothRodItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (player.fishing != null) {
            if (!level.isClientSide) {
                int i = player.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(i, player, interactionHand == InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
            }

            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                // 1.21: EnchantmentHelper.getFishingSpeedBonus/getLuckBonus removed - use registry lookup
                int k = greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(level, Enchantments.LURE, itemstack);
                int j = greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(level, Enchantments.LUCK_OF_THE_SEA, itemstack);
                level.addFreshEntity(new DragonToothHook(player, level, j, k));
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void inventoryTick(final ItemStack stack, final Level level, final Entity entityIn,
                              final int itemSlot, final boolean isSelected) {
        if (greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(level, FISHING_LUCK, stack) < FISHING_LUCK_LEVEL) {
            greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, FISHING_LUCK, FISHING_LUCK_LEVEL);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player playerIn) {
        if (greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(level, FISHING_LUCK, stack) < FISHING_LUCK_LEVEL) {
            greekfantasy.enchantment.EnchantmentUtils.enchant(level, stack, FISHING_LUCK, FISHING_LUCK_LEVEL);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Note: getEnchantmentTags() removed in 1.21, use isEnchanted()
        return stack.isEnchanted();
    }
}
