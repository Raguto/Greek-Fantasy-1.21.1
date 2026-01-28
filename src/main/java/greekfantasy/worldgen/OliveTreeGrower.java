package greekfantasy.worldgen;

import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class OliveTreeGrower {

    private static final ResourceKey<ConfiguredFeature<?, ?>> OLIVE_TREE = ResourceKey.create(
            net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "olive_tree"));

    public static final TreeGrower GROWER = new TreeGrower(
            "olive",
            Optional.empty(), // mega tree
            Optional.of(OLIVE_TREE), // tree
            Optional.empty()  // flowers
    );
}
