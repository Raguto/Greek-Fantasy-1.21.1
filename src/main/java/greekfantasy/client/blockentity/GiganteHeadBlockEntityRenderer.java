package greekfantasy.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import greekfantasy.blockentity.MobHeadBlockEntity;
import greekfantasy.client.blockentity.model.GiganteHeadModel;
import greekfantasy.client.entity.GiganteRenderer;
import greekfantasy.client.entity.model.GiganteModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GiganteHeadBlockEntityRenderer extends MobHeadBlockEntityRenderer {

    public GiganteHeadBlockEntityRenderer(final BlockEntityRendererProvider.Context context) {
        super(context, new GiganteHeadModel(context.getModelSet().bakeLayer(GiganteHeadModel.GIGANTE_HEAD_MODEL_RESOURCE)));
    }

    @Override
    public ResourceLocation getTexture() {
        return GiganteRenderer.TEXTURE;
    }

    @Override
    public float getScale() {
        return GiganteRenderer.SCALE;
    }

    @Override
    public void applyRotations(PoseStack poseStack, final boolean isOnWall) {
        // shift model down
        poseStack.translate(0.6D, 1.0D, 0.6D);
    }

    @Override
    public boolean shouldRenderOffScreen(MobHeadBlockEntity blockEntity) {
        return true;
    }

    public static class GiganteHeadItemStackRenderer extends BlockEntityWithoutLevelRenderer {
        private static final ResourceLocation TEXTURE = GiganteRenderer.TEXTURE;
        private final EntityModelSet entityModelSet;
        private GiganteHeadModel model;

        public GiganteHeadItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet entityModelSet) {
            super(dispatcher, entityModelSet);
            this.entityModelSet = entityModelSet;
            this.model = new GiganteHeadModel(this.entityModelSet.bakeLayer(GiganteModel.GIGANTE_MODEL_RESOURCE));
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            this.model = new GiganteHeadModel(this.entityModelSet.bakeLayer(GiganteModel.GIGANTE_MODEL_RESOURCE));
        }

        @Override
        public void renderByItem(ItemStack item, ItemDisplayContext transform, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 4.0F / 16.0F, 0.0F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            VertexConsumer vertexBuilder = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(TEXTURE), false, item.hasFoil());
            model.renderToBuffer(poseStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

}
