package greekfantasy.client.entity;

import greekfantasy.GreekFantasy;
import greekfantasy.client.entity.model.NymphModel;
import greekfantasy.entity.Dryad;
import greekfantasy.entity.util.NymphVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class DryadRenderer<T extends Dryad> extends MobRenderer<T, NymphModel<T>> {

    public static final Map<NymphVariant, ResourceLocation> TEXTURE_MAP = new HashMap<>();

    static {
        TEXTURE_MAP.put(Dryad.Variant.ACACIA, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/acacia.png"));
        TEXTURE_MAP.put(Dryad.Variant.BIRCH, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/birch.png"));
        TEXTURE_MAP.put(Dryad.Variant.DARK_OAK, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/dark_oak.png"));
        TEXTURE_MAP.put(Dryad.Variant.JUNGLE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/jungle.png"));
        TEXTURE_MAP.put(Dryad.Variant.OAK, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/oak.png"));
        TEXTURE_MAP.put(Dryad.Variant.OLIVE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/olive.png"));
        TEXTURE_MAP.put(Dryad.Variant.SPRUCE, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/dryad/spruce.png"));
    }

    public DryadRenderer(EntityRendererProvider.Context context) {
        super(context, new NymphModel<>(context.bakeLayer(NymphModel.NYMPH_LAYER_LOCATION), false), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(final T entity) {
        return TEXTURE_MAP.get(entity.getVariant());
    }
}
