package greekfantasy.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import greekfantasy.GreekFantasy;
import greekfantasy.client.entity.model.MinotaurModel;
import greekfantasy.entity.monster.Minotaur;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MinotaurRenderer<T extends Minotaur> extends HumanoidMobRenderer<T, MinotaurModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/minotaur.png");

    public MinotaurRenderer(EntityRendererProvider.Context context) {
        super(context, new MinotaurModel<>(context.bakeLayer(MinotaurModel.MINOTAUR_MODEL_RESOURCE)), 0.5F);
    }

    @Override
    protected void scale(final T entity, PoseStack poseStack, float partialTick) {
        // if the entity is charging, rotate the entity forward
        if (entity.isCharging()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(9.0F));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}