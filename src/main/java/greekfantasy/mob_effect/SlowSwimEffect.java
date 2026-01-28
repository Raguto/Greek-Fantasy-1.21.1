package greekfantasy.mob_effect;

import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;

public class SlowSwimEffect extends MobEffect {

    public SlowSwimEffect() {
        super(MobEffectCategory.HARMFUL, 0x4c423f);
        this.addAttributeModifier(NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "slow_swim"), -0.15000000596046448D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
