package greekfantasy.item;

import net.minecraft.core.Holder;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SnakeskinArmorItem extends ArmorItem {

    private static final String TEXTURE_1 = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/models/armor/snakeskin_layer_1.png").toString();
    private static final String TEXTURE_2 = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/models/armor/snakeskin_layer_2.png").toString();


    public SnakeskinArmorItem(final Holder<ArmorMaterial> armorMaterial, final Type slot, Properties builderIn) {
        super(armorMaterial, slot, builderIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        // TODO_ENCHANT: Poisoning enchantment disabled
        // if (GreekFantasy.CONFIG.isPoisoningEnabled() && stack.getEnchantmentLevel(null) < 1) {
        //     stack.enchant(...);
        // }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Note: getEnchantmentTags() removed in 1.21, use isEnchanted()
        return GreekFantasy.CONFIG.isPoisoningEnabled() ? stack.isEnchanted() : super.isFoil(stack);
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    @Override
    public void inventoryTick(final ItemStack stack, final Level worldIn, final Entity entityIn,
                              final int itemSlot, final boolean isSelected) {
        // TODO_ENCHANT: Poisoning enchantment disabled
        // if (GreekFantasy.CONFIG.isPoisoningEnabled() && stack.getEnchantmentLevel(null) < 1) {
        //     stack.enchant(...);
        // }
    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse((slot == EquipmentSlot.LEGS) ? TEXTURE_2 : TEXTURE_1);
    }
}
