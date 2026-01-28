package greekfantasy.client.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import greekfantasy.GreekFantasy;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpearModel extends Model {

    public static final ModelLayerLocation SPEAR_MODEL_RESOURCE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "spear"), "spear");
    ;

    private final ModelPart root;

    public SpearModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
    }

    @Override
    public void renderToBuffer(final PoseStack poseStack, final VertexConsumer vertexConsumer, final int packedLight, final int packedOverlay, final int color) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}