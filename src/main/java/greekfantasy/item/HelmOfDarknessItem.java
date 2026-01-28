package greekfantasy.item;

import greekfantasy.GreekFantasy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelmOfDarknessItem extends ArmorItem {

    private static final String TEXTURE = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/models/armor/avernal_layer_1.png").toString();

    // 1.21: ArmorItem now takes Holder<ArmorMaterial> instead of ArmorMaterial
    public HelmOfDarknessItem(final Holder<ArmorMaterial> armorMaterial, Properties builderIn) {
        super(armorMaterial, Type.HELMET, builderIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 11;
    }

    @Override
    public void inventoryTick(final ItemStack stack, final Level level, final Entity entity,
                              final int itemSlot, final boolean isSelected) {
        // add invisibility effect
        if (entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(this)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse(TEXTURE);
    }
}
