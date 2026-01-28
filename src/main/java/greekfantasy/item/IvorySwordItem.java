package greekfantasy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class IvorySwordItem extends SwordItem {

    public static final ResourceLocation ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ivory_sword_attack_damage");
    public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ivory_sword_attack_speed");
    public static final ResourceLocation ATTACK_KNOCKBACK_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ivory_sword_attack_knockback");
    public static final double ATTACK_KNOCKBACK_AMOUNT = 1.25D;
    protected final Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMap;

    public IvorySwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        // Note: In 1.21, SwordItem constructor changed - attack stats set via Item.Properties.attributes()
        super(tier, properties);
        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_ID, attackDamageIn + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ID, attackSpeedIn, AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_ID, ATTACK_KNOCKBACK_AMOUNT, AttributeModifier.Operation.ADD_VALUE));
        this.attributeModifierMap = builder.build();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise the damage
     * on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        // apply knockback to the entity that was hit
        if (attacker instanceof Player player && !player.getCooldowns().isOnCooldown(this)) {
            // determine knockback amount - use stored value since getAttributeModifiers removed
            float knockback = (float) ATTACK_KNOCKBACK_AMOUNT;
            target.knockback(knockback * 0.75F, Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F)));
        }
        return true;
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers should be set via Item.Properties.attributes() in registration
}
