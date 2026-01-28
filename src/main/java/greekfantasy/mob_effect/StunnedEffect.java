package greekfantasy.mob_effect;

import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

public class StunnedEffect extends MobEffect {

    public StunnedEffect() {
        super(MobEffectCategory.HARMFUL, 0xC0C0C0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "stunned_movement"), -1.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "stunned_swim"), -1.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
