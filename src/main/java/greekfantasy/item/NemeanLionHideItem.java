package greekfantasy.item;

import net.minecraft.core.Holder;

import greekfantasy.GreekFantasy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NemeanLionHideItem extends ArmorItem {

    private static final String TEXTURE_1 = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/models/armor/nemean_layer_1.png").toString();

    public NemeanLionHideItem(final Holder<ArmorMaterial> armorMaterial, final Type slot, Properties builderIn) {
        super(armorMaterial, slot, builderIn);
    }

    public static boolean isImmune(final LivingEntity entity, final Projectile projectile, final double dotProduct) {
        // determine if projectile is facing the same direction as player
        return dotProduct > 0.70D;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GOLD));
    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse(TEXTURE_1);
    }

    // 1.21: initializeClient removed - use RegisterClientExtensionsEvent in GFClientEvents instead
}
