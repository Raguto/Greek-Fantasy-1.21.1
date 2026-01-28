package greekfantasy.mob_effect;

import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

public class CurseOfCirceEffect extends MobEffect {

    public static final double HEALTH_MODIFIER = -10.0D;

    public CurseOfCirceEffect() {
        super(MobEffectCategory.HARMFUL, 0xF19E98);
        this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_health"), HEALTH_MODIFIER, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_attack"), -0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_knockback"), -0.8D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        // Note: In 1.21, ENTITY_REACH and BLOCK_REACH are now vanilla Attributes.ENTITY_INTERACTION_RANGE and Attributes.BLOCK_INTERACTION_RANGE
        this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_attack_range"), -1.0D, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_reach"), -1.0D, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_swim"), -0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
