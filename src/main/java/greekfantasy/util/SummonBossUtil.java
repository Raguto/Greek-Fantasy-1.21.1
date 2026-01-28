package greekfantasy.util;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.entity.Automaton;
import greekfantasy.entity.boss.BronzeBull;
import greekfantasy.entity.boss.Cerberus;
import greekfantasy.entity.boss.Talos;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SummonBossUtil {

    public static final TagKey<Block> BRONZE_BLOCK = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("forge", "storage_blocks/bronze"));
    public static final TagKey<Block> BRONZE_BLOCK_COMMON = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/bronze"));
    public static final TagKey<Block> COPPER_BLOCK = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("forge", "storage_blocks/any_copper"));
    public static final TagKey<Block> COPPER_BLOCK_COMMON = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/any_copper"));
    public static final TagKey<Block> COPPER_BLOCK_STORAGE = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("forge", "storage_blocks/copper"));
    public static final TagKey<Block> COPPER_BLOCK_STORAGE_COMMON = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/copper"));
    public static final TagKey<Block> CERBERUS_FRAME = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "cerberus_frame"));

    private static boolean isBronzeBlock(final BlockState state) {
        return state.is(BRONZE_BLOCK)
            || state.is(BRONZE_BLOCK_COMMON)
            || state.is(GFRegistry.BlockReg.BRONZE_BLOCK.get());
    }

    private static boolean isCopperBlock(final BlockState state) {
        return state.is(COPPER_BLOCK)
            || state.is(COPPER_BLOCK_COMMON)
            || state.is(COPPER_BLOCK_STORAGE)
            || state.is(COPPER_BLOCK_STORAGE_COMMON)
            || isVanillaCopperBlock(state);
    }

    public static boolean isSummonBlock(final BlockState state) {
        return isBronzeBlock(state) || isCopperBlock(state);
    }

    private static boolean isCopperBaseBlock(final BlockState state) {
        return isCopperBlock(state) && !isBronzeBlock(state);
    }

    private static boolean isVanillaCopperBlock(final BlockState state) {
        return state.is(Blocks.COPPER_BLOCK)
            || state.is(Blocks.EXPOSED_COPPER)
            || state.is(Blocks.WEATHERED_COPPER)
            || state.is(Blocks.OXIDIZED_COPPER)
            || state.is(Blocks.CUT_COPPER)
            || state.is(Blocks.EXPOSED_CUT_COPPER)
            || state.is(Blocks.WEATHERED_CUT_COPPER)
            || state.is(Blocks.OXIDIZED_CUT_COPPER)
            || state.is(Blocks.WAXED_COPPER_BLOCK)
            || state.is(Blocks.WAXED_EXPOSED_COPPER)
            || state.is(Blocks.WAXED_WEATHERED_COPPER)
            || state.is(Blocks.WAXED_OXIDIZED_COPPER)
            || state.is(Blocks.WAXED_CUT_COPPER)
            || state.is(Blocks.WAXED_EXPOSED_CUT_COPPER)
            || state.is(Blocks.WAXED_WEATHERED_CUT_COPPER)
            || state.is(Blocks.WAXED_OXIDIZED_CUT_COPPER);
    }

    private static boolean isBronzeAt(final Level level, final BlockPos pos) {
        return isBronzeBlock(level.getBlockState(pos));
    }

    private static boolean isCopperAt(final Level level, final BlockPos pos) {
        return isCopperBlock(level.getBlockState(pos));
    }

    private static boolean isCopperBaseAt(final Level level, final BlockPos pos) {
        return isCopperBaseBlock(level.getBlockState(pos));
    }

    private static boolean isCerberusBaseBlock(final BlockState state) {
        return state.is(CERBERUS_FRAME)
                || state.is(Blocks.BLACKSTONE)
                || state.is(Blocks.POLISHED_BLACKSTONE)
                || state.is(Blocks.POLISHED_BLACKSTONE_BRICKS)
                || state.is(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
                || state.is(Blocks.CHISELED_POLISHED_BLACKSTONE)
                || state.is(Blocks.GILDED_BLACKSTONE);
    }

    @Nullable
    private static BlockPos findCerberusBase(final Level level, final BlockPos lavaPos) {
        int baseY = lavaPos.getY();
        for (int baseX = lavaPos.getX() - 3; baseX <= lavaPos.getX(); baseX++) {
            for (int baseZ = lavaPos.getZ() - 3; baseZ <= lavaPos.getZ(); baseZ++) {
                boolean validStructure = true;
                for (int dx = 0; dx < 4 && validStructure; dx++) {
                    for (int dz = 0; dz < 4; dz++) {
                        BlockPos checkPos = new BlockPos(baseX + dx, baseY, baseZ + dz);
                        boolean isCenter = dx >= 1 && dx <= 2 && dz >= 1 && dz <= 2;
                        if (isCenter) {
                            if (!level.getBlockState(checkPos).is(Blocks.LAVA)) {
                                validStructure = false;
                                break;
                            }
                        } else if (!isCerberusBaseBlock(level.getBlockState(checkPos))) {
                            validStructure = false;
                            break;
                        }
                    }
                }
                if (!validStructure) {
                    continue;
                }

                boolean headInCenter = lavaPos.getX() >= baseX + 1 && lavaPos.getX() <= baseX + 2
                        && lavaPos.getZ() >= baseZ + 1 && lavaPos.getZ() <= baseZ + 2;
                if (headInCenter) {
                    return new BlockPos(baseX, baseY, baseZ);
                }
            }
        }
        return null;
    }

    private static BlockPos[][] getBronzeBullHornPairsStrict(final int baseX, final int baseY, final int baseZ) {
        List<BlockPos[]> pairs = new ArrayList<>();
        int[] hornYs = new int[]{baseY + 1, baseY + 2};
        for (int hornY : hornYs) {
            // horns on opposite sides (front/back) of the copper base
            pairs.add(new BlockPos[]{new BlockPos(baseX, hornY, baseZ - 1), new BlockPos(baseX, hornY, baseZ + 2)});
            pairs.add(new BlockPos[]{new BlockPos(baseX + 1, hornY, baseZ - 1), new BlockPos(baseX + 1, hornY, baseZ + 2)});
            // horns on opposite sides (left/right) of the copper base
            pairs.add(new BlockPos[]{new BlockPos(baseX - 1, hornY, baseZ), new BlockPos(baseX + 2, hornY, baseZ)});
            pairs.add(new BlockPos[]{new BlockPos(baseX - 1, hornY, baseZ + 1), new BlockPos(baseX + 2, hornY, baseZ + 1)});
        }
        return pairs.toArray(new BlockPos[0][]);
    }

    /**
     * BlockPattern for Cerberus boss
     **/
    private static final BlockPattern cerberusPattern = BlockPatternBuilder.start()
            .aisle("~##~", "~~~~", "~~~~")
            .aisle("#^^#", "~OO~", "~OO~")
            .aisle("#^^#", "~OO~", "~OO~")
            .aisle("~##~", "~~~~", "~~~~")
            .where('#', BlockInWorld.hasState(state -> state.is(CERBERUS_FRAME)))
            .where('^', BlockInWorld.hasState(state -> state.is(Blocks.LAVA)))
            .where('O', BlockInWorld.hasState(BlockBehaviour.BlockStateBase::isAir))
            .where('~', BlockInWorld.hasState(state -> true)).build();

    /**
     * BlockPattern for Automaton
     **/
    private static final BlockPattern automatonPattern = BlockPatternBuilder.start()
            .aisle("^", "#")
            .where('^', BlockInWorld.hasState(SummonBossUtil::isBronzeBlock))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(GFRegistry.BlockReg.ICHOR_INFUSED_GEARBOX.get())))
            .build();


    /**
     * Called when a block in the {@link #BRONZE_BLOCK} tag is placed. Checks if a boss stucture was formed,
     * and if so, removes the blocks and summons the boss.
     *
     * @param level  the level
     * @param pos    the position of the block
     * @param state  the block state
     * @param placer the entity who placed the block, if any
     * @return true if the boss was summoned and the structure was replaced
     */
    public static boolean onPlaceBronzeBlock(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
        if (!isBronzeBlock(state) && !isCopperBlock(state)) {
            return false;
        }
        // check if an automaton was built
        BlockPattern pattern = automatonPattern;
        BlockPattern.BlockPatternMatch helper = pattern.find(level, pos);
        if (helper != null) {
            // remove the blocks that were used
            for (int i = 0; i < pattern.getWidth(); ++i) {
                for (int j = 0; j < pattern.getHeight(); ++j) {
                    for (int k = 0; k < pattern.getDepth(); ++k) {
                        BlockInWorld cachedblockinfo1 = helper.getBlock(i, j, k);
                        level.destroyBlock(cachedblockinfo1.getPos(), false);
                    }
                }
            }
            // spawn the automaton
            Automaton.spawnAutomaton(level, helper.getBlock(0, 1, 0).getPos(), 0);
            return true;
        }
        // check if a talos was built (3x2 copper base with bronze block on top)
        Player debugPlayer = null;
        if (placer instanceof Player placerPlayer && level instanceof ServerLevel) {
            debugPlayer = placerPlayer;
        }
        if (trySummonTalos(level, pos, state, debugPlayer)) {
            return true;
        }
        // fallback: strict 2x2x2 copper base with two bronze horns on top edge
        boolean fallbackSummoned = trySummonBronzeBullFallback(level, pos, state, debugPlayer);
        if (fallbackSummoned) {
            return true;
        }
        return false;
    }

    private static boolean trySummonTalos(final Level level, final BlockPos pos, final BlockState placedState,
                                          @Nullable final Player debugPlayer) {
        if (isBronzeBlock(placedState)) {
            boolean summoned = trySummonTalosFromBronze(level, pos);
            if (!summoned && debugPlayer != null) {
                reportTalosFailure(level, debugPlayer, pos);
            }
            return summoned;
        }
        if (isCopperBaseBlock(placedState)) {
            BlockPos bronzePos = pos.above();
            if (isBronzeAt(level, bronzePos)) {
                boolean summoned = trySummonTalosFromBronze(level, bronzePos);
                if (!summoned && debugPlayer != null) {
                    reportTalosFailure(level, debugPlayer, bronzePos);
                }
                return summoned;
            }
        }
        return false;
    }

    private static boolean trySummonTalosFromBronze(final Level level, final BlockPos bronzePos) {
        // Talos ritual: vertical 2x3 copper wall with bronze on top
        return trySummonTalosVertical(level, bronzePos);
    }

    private static boolean trySummonTalosVertical(final Level level, final BlockPos bronzePos) {
        int bronzeX = bronzePos.getX();
        int bronzeZ = bronzePos.getZ();
        int baseY = bronzePos.getY() - 2;
        for (int dx = 0; dx < 3; dx++) {
            int baseX = bronzeX - dx;
            int baseZ = bronzeZ;
            if (trySummonTalosVerticalAt(level, baseX, baseY, baseZ, true, bronzePos)) {
                return true;
            }
            baseX = bronzeX;
            baseZ = bronzeZ - dx;
            if (trySummonTalosVerticalAt(level, baseX, baseY, baseZ, false, bronzePos)) {
                return true;
            }
        }
        return false;
    }

    private static boolean trySummonTalosVerticalAt(final Level level, final int baseX, final int baseY, final int baseZ,
                                                    final boolean alongX, final BlockPos bronzePos) {
        boolean bronzeInFootprint = false;
        for (int dx = 0; dx < 3; dx++) {
            for (int dy = 0; dy < 2; dy++) {
                int x = alongX ? baseX + dx : baseX;
                int z = alongX ? baseZ : baseZ + dx;
                BlockPos checkPos = new BlockPos(x, baseY + dy, z);
                if (checkPos.equals(bronzePos)) {
                    if (!isBronzeAt(level, checkPos)) {
                        return false;
                    }
                    bronzeInFootprint = true;
                    continue;
                }
                if (!isCopperBaseAt(level, checkPos)) {
                    return false;
                }
                BlockPos topPos = checkPos.above();
                if (topPos.equals(bronzePos)) {
                    bronzeInFootprint = true;
                }
            }
        }

        for (int dx = 0; dx < 3; dx++) {
            for (int dy = 0; dy < 2; dy++) {
                int x = alongX ? baseX + dx : baseX;
                int z = alongX ? baseZ : baseZ + dx;
                BlockPos checkPos = new BlockPos(x, baseY + dy, z);
                if (!checkPos.equals(bronzePos)) {
                    level.destroyBlock(checkPos, false);
                }
            }
        }
        level.destroyBlock(bronzePos, false);
        BlockPos spawnPos = new BlockPos(baseX + 1, baseY + 1, baseZ + (alongX ? 0 : 1));
        Talos.spawnTalos(level, spawnPos, 0);
        return true;
    }

    @SuppressWarnings("null")
    private static void reportTalosFailure(final Level level, final Player player, final BlockPos bronzePos) {
        // Debug messages removed
    }

    @SuppressWarnings("null")
    private static TalosMatchInfo getTalosBestCopperMatch(final Level level, final BlockPos bronzePos, final int width, final int depth) {
        int baseY = bronzePos.getY() - 1;
        int bronzeX = bronzePos.getX();
        int bronzeZ = bronzePos.getZ();
        int bestCount = 0;
        List<String> bestMismatches = List.of();
        for (int dx = 0; dx < width; dx++) {
            for (int dz = 0; dz < depth; dz++) {
                int baseX = bronzeX - dx;
                int baseZ = bronzeZ - dz;
                int count = 0;
                List<String> mismatches = new ArrayList<>();
                for (int ix = 0; ix < width; ix++) {
                    for (int iz = 0; iz < depth; iz++) {
                        BlockPos checkPos = new BlockPos(baseX + ix, baseY, baseZ + iz);
                        if (isCopperBaseAt(level, checkPos)) {
                            count++;
                        } else if (mismatches.size() < 3) {
                            BlockState state = level.getBlockState(checkPos);
                            ResourceLocation key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                            mismatches.add(checkPos.toShortString() + "=" + key);
                        }
                    }
                }
                if (count > bestCount) {
                    bestCount = count;
                    bestMismatches = mismatches;
                }
            }
        }
        return new TalosMatchInfo(bestCount, bestMismatches);
    }

    private static final class TalosMatchInfo {
        private final int matchCount;
        private final List<String> mismatches;

        private TalosMatchInfo(final int matchCount, final List<String> mismatches) {
            this.matchCount = matchCount;
            this.mismatches = mismatches;
        }
    }

    private static boolean trySummonTalosAt(final Level level, final int baseX, final int baseY, final int baseZ,
                                            final int width, final int depth, final BlockPos bronzePos) {
        boolean bronzeInFootprint = false;
        boolean bronzeInBaseLayer = false;
        for (int dx = 0; dx < width; dx++) {
            for (int dz = 0; dz < depth; dz++) {
                BlockPos basePos = new BlockPos(baseX + dx, baseY, baseZ + dz);
                if (basePos.equals(bronzePos)) {
                    if (!isBronzeAt(level, basePos)) {
                        return false;
                    }
                    bronzeInFootprint = true;
                    bronzeInBaseLayer = true;
                    continue;
                }
                if (!isCopperBaseAt(level, basePos)) {
                    return false;
                }
                BlockPos topPos = basePos.above();
                if (topPos.equals(bronzePos)) {
                    bronzeInFootprint = true;
                }
            }
        }

        if (!bronzeInFootprint) {
            return false;
        }
        if (!bronzeInBaseLayer && !isBronzeAt(level, bronzePos)) {
            return false;
        }

        for (int dx = 0; dx < width; dx++) {
            for (int dz = 0; dz < depth; dz++) {
                level.destroyBlock(new BlockPos(baseX + dx, baseY, baseZ + dz), false);
            }
        }
        if (!bronzeInBaseLayer) {
            level.destroyBlock(bronzePos, false);
        }
        BlockPos spawnPos = new BlockPos(baseX + width / 2, baseY + 1, baseZ + depth / 2);
        Talos.spawnTalos(level, spawnPos, 0);
        return true;
    }

    private static boolean trySummonBronzeBullFallback(final Level level, final BlockPos pos, final BlockState placedState, @Nullable final Player player) {
        boolean placedIsBronze = isBronzeBlock(placedState);
        boolean placedIsCopper = isCopperBaseBlock(placedState);
        for (int baseY = pos.getY() - 2; baseY <= pos.getY(); baseY++) {
            for (int baseX = pos.getX() - 2; baseX <= pos.getX() + 1; baseX++) {
                for (int baseZ = pos.getZ() - 2; baseZ <= pos.getZ() + 1; baseZ++) {
                    BlockPos basePos = new BlockPos(baseX, baseY, baseZ);
                    BlockPos basePosX = new BlockPos(baseX + 1, baseY, baseZ);
                    BlockPos basePosZ = new BlockPos(baseX, baseY, baseZ + 1);
                    BlockPos basePosXZ = new BlockPos(baseX + 1, baseY, baseZ + 1);
                    BlockPos topBasePos = new BlockPos(baseX, baseY + 1, baseZ);
                    BlockPos topBasePosX = new BlockPos(baseX + 1, baseY + 1, baseZ);
                    BlockPos topBasePosZ = new BlockPos(baseX, baseY + 1, baseZ + 1);
                    BlockPos topBasePosXZ = new BlockPos(baseX + 1, baseY + 1, baseZ + 1);

                    if (!isCopperBaseAt(level, basePos)
                            || !isCopperBaseAt(level, basePosX)
                            || !isCopperBaseAt(level, basePosZ)
                            || !isCopperBaseAt(level, basePosXZ)
                            || !isCopperBaseAt(level, topBasePos)
                            || !isCopperBaseAt(level, topBasePosX)
                            || !isCopperBaseAt(level, topBasePosZ)
                            || !isCopperBaseAt(level, topBasePosXZ)) {
                        continue;
                    }

                    BlockPos[][] hornOptions = getBronzeBullHornPairsStrict(baseX, baseY, baseZ);

                    for (BlockPos[] horns : hornOptions) {
                        if (!isBronzeAt(level, horns[0]) || !isBronzeAt(level, horns[1])) {
                            continue;
                        }
                        if (placedIsBronze && !horns[0].equals(pos) && !horns[1].equals(pos)) {
                            continue;
                        }
                        if (placedIsCopper) {
                            boolean placedInBase = pos.equals(basePos) || pos.equals(basePosX) || pos.equals(basePosZ) || pos.equals(basePosXZ)
                                || pos.equals(topBasePos) || pos.equals(topBasePosX) || pos.equals(topBasePosZ) || pos.equals(topBasePosXZ);
                            if (!placedInBase) {
                                continue;
                            }
                        }

                        level.destroyBlock(basePos, false);
                        level.destroyBlock(basePosX, false);
                        level.destroyBlock(basePosZ, false);
                        level.destroyBlock(basePosXZ, false);
                        level.destroyBlock(topBasePos, false);
                        level.destroyBlock(topBasePosX, false);
                        level.destroyBlock(topBasePosZ, false);
                        level.destroyBlock(topBasePosXZ, false);
                        level.destroyBlock(horns[0], false);
                        level.destroyBlock(horns[1], false);
                        Vec3 spawnPos = new Vec3(baseX + 1.0D, baseY + 0.1D, baseZ + 1.0D);
                        BronzeBull.spawnBronzeBull(level, spawnPos, 0);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Called when an Orthus Head item entity is removed while on fire. Checks if a boss structure was formed,
     * and if so, removes the blocks and summons the boss.
     * @param level the level
     * @param pos the block position
     * @param thrower the entity that burned the orthus head, may be null
     * @return true if the boss was summoned and the structure was replaced
     */
    public static boolean onOrthusHeadBurned(Level level, BlockPos pos, @Nullable UUID thrower) {
        BlockPos cerberusBase = findCerberusBase(level, pos);
        if (cerberusBase != null) {
            for (int dx = 0; dx < 2; dx++) {
                for (int dz = 0; dz < 2; dz++) {
                    BlockPos lavaPos = cerberusBase.offset(1 + dx, 0, 1 + dz);
                    if (level.getBlockState(lavaPos).is(Blocks.LAVA)) {
                        level.setBlock(lavaPos, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }
            Cerberus.spawnCerberus(level, Vec3.atBottomCenterOf(pos.above()));
            return true;
        }
        // check if a cerberus frame was built
        BlockPattern pattern = cerberusPattern;
        BlockPattern.BlockPatternMatch helper = pattern.find(level, pos);
        if (helper == null) {
            search:
            for (int dx = -2; dx <= 2; dx++) {
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        helper = pattern.find(level, pos.offset(dx, dy, dz));
                        if (helper != null) {
                            break search;
                        }
                    }
                }
            }
        }
        if (helper != null) {
            // replace the lava blocks that were used
            for (int i = 1; i < pattern.getWidth() - 1; ++i) {
                for (int k = 1; k < pattern.getDepth() - 1; ++k) {
                    BlockInWorld cachedblockinfo1 = helper.getBlock(i, 0, k);
                    level.setBlock(cachedblockinfo1.getPos(), Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
            // spawn the cerberus
            Cerberus.spawnCerberus(level, Vec3.atBottomCenterOf(pos.above()));
            return true;
        }
        return false;
    }
}