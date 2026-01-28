package greekfantasy.item;

import net.minecraft.core.Holder;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WingedSandalsItem extends ArmorItem {

    public static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "winged_sandals_speed");
    public static final int BROKEN = 2;

    private static final String TEXTURE = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/models/armor/winged_layer_2.png").toString();

    protected Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers;

    public WingedSandalsItem(final Holder<ArmorMaterial> armorMaterial, Properties builderIn) {
        super(armorMaterial, Type.BOOTS, builderIn);
        // Note: In 1.21, attribute modifiers are set via Item.Properties.attributes()
        // The old getDefaultAttributeModifiers pattern no longer works
        // Speed bonus should be applied via equipment attributes or mob effects
        final double speedBonus = 1.5F;
        final double stepHeightBonus = 0.62F;
        // TODO 1.21: Migrate attribute modifiers to Item.Properties.attributes() or DataComponents
        this.attributeModifiers = ImmutableMultimap.of();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    @Override
    public void inventoryTick(final ItemStack stack, final Level level, final Entity entity,
                              final int itemSlot, final boolean isSelected) {
        // TODO_ENCHANT: Overstep enchantment disabled
        // if (GreekFantasy.CONFIG.isOverstepEnabled() && stack.getEnchantmentLevel(null) < 1) {
        //     stack.enchant(...);
        // }
        // add mob effects
        if (entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.FEET).is(this) && stack.getMaxDamage() - stack.getDamageValue() > BROKEN) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 30, 4, false, false, false));
            livingEntity.fallDistance = 0;
            // damage the item
            if (level.getRandom().nextFloat() < GreekFantasy.CONFIG.getWingedSandalsDurabilityChance()) {
                stack.hurtAndBreak(1, livingEntity, EquipmentSlot.FEET);
            }
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        // TODO_ENCHANT: Overstep enchantment disabled
        // if (GreekFantasy.CONFIG.isOverstepEnabled() && stack.getEnchantmentLevel(null) < 1) {
        //     stack.enchant(...);
        // }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Note: getEnchantmentTags() removed in 1.21, use isEnchanted() or check component
        return GreekFantasy.CONFIG.isOverstepEnabled() ? stack.isEnchanted() : super.isFoil(stack);
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers are now set via Item.Properties.attributes() or DataComponents
    // For dynamic attributes based on item damage, you need a different approach

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        // add broken tooltip
        if (stack.getMaxDamage() - stack.getDamageValue() <= BROKEN) {
            tooltip.add(Component.translatable("item.tooltip.broken").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
        // add jump boost tooltip
        tooltip.add(Component.translatable(MobEffects.JUMP.value().getDescriptionId()).withStyle(ChatFormatting.AQUA)
                .append(" ").append(Component.translatable("enchantment.level.5").withStyle(ChatFormatting.AQUA)));

    }

    @Nullable
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse(TEXTURE);
    }

    // 1.21: initializeClient removed - use RegisterClientExtensionsEvent in GFClientEvents instead
}
