package greekfantasy.enchantment;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Utility methods for working with enchantments in 1.21's data-driven system
 */
public class EnchantmentUtils {

    /**
     * Gets a Holder for an enchantment ResourceKey
     */
    @Nullable
    public static Holder<Enchantment> getHolder(Level level, ResourceKey<Enchantment> enchantmentKey) {
        if (level == null || level.registryAccess().registry(Registries.ENCHANTMENT).isEmpty()) {
            return null;
        }
        var registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        return registry.getHolder(enchantmentKey).orElse(null);
    }

    /**
     * Enchants an ItemStack with a ResourceKey enchantment
     */
    public static void enchant(Level level, ItemStack stack, ResourceKey<Enchantment> enchantmentKey, int level_) {
        Holder<Enchantment> holder = getHolder(level, enchantmentKey);
        if (holder != null) {
            stack.enchant(holder, level_);
        }
    }

    /**
     * Gets the enchantment level for a ResourceKey on an ItemStack
     */
    public static int getEnchantmentLevel(Level level, ResourceKey<Enchantment> enchantmentKey, ItemStack stack) {
        if (level == null || level.registryAccess().registry(Registries.ENCHANTMENT).isEmpty()) {
            return 0;
        }
        var registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> holder = registry.getHolder(enchantmentKey).orElse(null);
        if (holder == null) {
            return 0;
        }
        return EnchantmentHelper.getItemEnchantmentLevel(holder, stack);
    }

    /**
     * Gets the enchantment level for a ResourceKey on a LivingEntity's equipment
     */
    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantmentKey, LivingEntity entity, ItemStack stack) {
        if (entity == null || entity.level() == null) {
            return 0;
        }
        return getEnchantmentLevel(entity.level(), enchantmentKey, stack);
    }

    /**
     * Checks if an ItemStack has a specific enchantment
     */
    public static boolean hasEnchantment(Level level, ResourceKey<Enchantment> enchantmentKey, ItemStack stack) {
        return getEnchantmentLevel(level, enchantmentKey, stack) > 0;
    }
}
