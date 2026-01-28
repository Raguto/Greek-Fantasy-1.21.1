package greekfantasy.worldgen;

import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class PomegranateTreeGrower {

    private static final ResourceKey<ConfiguredFeature<?, ?>> POMEGRANATE_TREE = ResourceKey.create(
            net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "pomegranate_tree"));

    public static final TreeGrower GROWER = new TreeGrower(
            "pomegranate",
            Optional.empty(), // mega tree
            Optional.of(POMEGRANATE_TREE), // tree
            Optional.empty()  // flowers
    );
}
