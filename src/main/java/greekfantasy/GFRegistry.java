package greekfantasy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.world.StructureModifier;
import greekfantasy.block.CerberusHeadBlock;
import greekfantasy.block.GoldenStringBlock;
import greekfantasy.block.MobHeadBlock;
import greekfantasy.block.MysteriousBoxBlock;
import greekfantasy.block.NestBlock;
import greekfantasy.block.OilLampBlock;
import greekfantasy.block.OliveOilBlock;
import greekfantasy.block.OrthusHeadBlock;
import greekfantasy.block.PillarBlock;
import greekfantasy.block.PomegranateSaplingBlock;
import greekfantasy.block.ReedsBlock;
import greekfantasy.block.VaseBlock;
import greekfantasy.block.WildRoseBlock;
import greekfantasy.blockentity.MobHeadBlockEntity;
import greekfantasy.blockentity.VaseBlockEntity;
import greekfantasy.enchantment.GFEnchantments;
import greekfantasy.entity.Arion;
import greekfantasy.entity.Automaton;
import greekfantasy.entity.Centaur;
import greekfantasy.entity.Cerastes;
import greekfantasy.entity.Dryad;
import greekfantasy.entity.Elpis;
import greekfantasy.entity.Gigante;
import greekfantasy.entity.GoldenRam;
import greekfantasy.entity.Lampad;
import greekfantasy.entity.Makhai;
import greekfantasy.entity.Naiad;
import greekfantasy.entity.Orthus;
import greekfantasy.entity.Palladium;
import greekfantasy.entity.Pegasus;
import greekfantasy.entity.Satyr;
import greekfantasy.entity.Sparti;
import greekfantasy.entity.Triton;
import greekfantasy.entity.Unicorn;
import greekfantasy.entity.Whirl;
import greekfantasy.entity.boss.Arachne;
import greekfantasy.entity.boss.BronzeBull;
import greekfantasy.entity.boss.Cerberus;
import greekfantasy.entity.boss.Charybdis;
import greekfantasy.entity.boss.CretanMinotaur;
import greekfantasy.entity.boss.Geryon;
import greekfantasy.entity.boss.GiantBoar;
import greekfantasy.entity.boss.Hydra;
import greekfantasy.entity.boss.HydraHead;
import greekfantasy.entity.boss.NemeanLion;
import greekfantasy.entity.boss.Python;
import greekfantasy.entity.boss.Scylla;
import greekfantasy.entity.boss.Talos;
import greekfantasy.entity.misc.BronzeFeather;
import greekfantasy.entity.misc.Curse;
import greekfantasy.entity.misc.CurseOfCirce;
import greekfantasy.entity.misc.Discus;
import greekfantasy.entity.misc.DragonTooth;
import greekfantasy.entity.misc.DragonToothHook;
import greekfantasy.entity.misc.GreekFire;
import greekfantasy.entity.misc.HealingSpell;
import greekfantasy.entity.misc.PoisonSpit;
import greekfantasy.entity.misc.Spear;
import greekfantasy.entity.misc.ThrowingAxe;
import greekfantasy.entity.misc.WaterSpell;
import greekfantasy.entity.misc.WebBall;
import greekfantasy.entity.monster.Ara;
import greekfantasy.entity.monster.BabySpider;
import greekfantasy.entity.monster.Circe;
import greekfantasy.entity.monster.Cyclops;
import greekfantasy.entity.monster.Cyprian;
import greekfantasy.entity.monster.Drakaina;
import greekfantasy.entity.monster.Empusa;
import greekfantasy.entity.monster.Fury;
import greekfantasy.entity.monster.Gorgon;
import greekfantasy.entity.monster.Harpy;
import greekfantasy.entity.monster.MadCow;
import greekfantasy.entity.monster.Minotaur;
import greekfantasy.entity.monster.Shade;
import greekfantasy.entity.monster.Siren;
import greekfantasy.entity.monster.Stymphalian;
import greekfantasy.item.BagOfWindItem;
import greekfantasy.item.BidentItem;
import greekfantasy.item.BronzeFeatherItem;
import greekfantasy.item.BronzeScrapItem;
import greekfantasy.item.CerberusHeadItem;
import greekfantasy.item.ClubItem;
import greekfantasy.item.ConchItem;
import greekfantasy.item.DragonToothRodItem;
import greekfantasy.item.EnchantedBowItem;
import greekfantasy.item.GiganteHeadItem;
import greekfantasy.item.GreekFireItem;
import greekfantasy.item.HasCraftRemainderItem;
import greekfantasy.item.DiscusItem;
import greekfantasy.item.DragonToothItem;
import greekfantasy.item.GFArmorMaterials;
import greekfantasy.item.GFTiers;
import greekfantasy.item.GoldenBallItem;
import greekfantasy.item.GorgonBloodItem;
import greekfantasy.item.HellenicArmorItem;
import greekfantasy.item.HelmOfDarknessItem;
import greekfantasy.item.HornOfPlentyItem;
import greekfantasy.item.InstrumentItem;
import greekfantasy.item.IvorySwordItem;
import greekfantasy.item.KnifeItem;
import greekfantasy.item.NemeanLionHideItem;
import greekfantasy.item.OliveOilItem;
import greekfantasy.item.OliveSalveItem;
import greekfantasy.item.OrthusHeadItem;
import greekfantasy.item.PalladiumItem;
import greekfantasy.item.QuestItem;
import greekfantasy.item.SnakeskinArmorItem;
import greekfantasy.item.SpearItem;
import greekfantasy.item.StaffOfHealingItem;
import greekfantasy.item.ThrowingAxeItem;
import greekfantasy.item.ThunderboltItem;
import greekfantasy.item.ThyrsusItem;
import greekfantasy.item.UnicornHornItem;
import greekfantasy.item.WandOfCirceItem;
import greekfantasy.item.WebBallItem;
import greekfantasy.item.WingedSandalsItem;
import greekfantasy.mob_effect.CurseOfCirceEffect;
import greekfantasy.mob_effect.MirroringEffect;
import greekfantasy.mob_effect.SlowSwimEffect;
import greekfantasy.mob_effect.PrisonerOfHadesEffect;
import greekfantasy.mob_effect.StunnedEffect;
import greekfantasy.util.*;
import greekfantasy.worldgen.CentaurStructureProcessor;
import greekfantasy.worldgen.DimensionFilter;
import greekfantasy.worldgen.GoldenTreeGrower;
import greekfantasy.worldgen.HarpyNestFeature;
import greekfantasy.worldgen.LocStructureProcessor;
import greekfantasy.worldgen.OceanVillageStructure;
import greekfantasy.worldgen.OceanVillageStructureProcessor;
import greekfantasy.worldgen.OliveTreeFeature;
import greekfantasy.worldgen.OliveTreeGrower;
import greekfantasy.worldgen.PomegranateTreeGrower;
import greekfantasy.worldgen.SatyrStructureProcessor;
import greekfantasy.worldgen.maze.MazePiece;
import greekfantasy.worldgen.maze.MazeStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static greekfantasy.item.QuestItem.KEY_QUEST;

@SuppressWarnings({"unused", "RedundantTypeArguments"})
public final class GFRegistry {

    private static final String MODID = GreekFantasy.MODID;

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks( MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems( MODID);
    private static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, MODID);
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, MODID);
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MODID);
    // 1.21: Loot modifiers now use MapCodec instead of Codec
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
    // 1.21: Enchantments are now fully data-driven - this registry is no longer used
    // Enchantments are defined in data/greekfantasy/enchantment/*.json
    // private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, MODID);
    private static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
    private static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, MODID);
    private static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, MODID);
    // 1.21: Structure processors are now registered via DeferredRegister
    private static final DeferredRegister<net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, MODID);
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);
    // 1.21: Structure modifiers now use MapCodec instead of Codec
    private static final DeferredRegister<MapCodec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GreekFantasy.MODID);


    public static void register(IEventBus modEventBus) {
        // Register all deferred registers to the mod event bus
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BANNER_PATTERNS.register(modEventBus);
        POTIONS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        // ENCHANTMENTS.register(modEventBus); // 1.21: Enchantments are now fully data-driven
        ENTITY_TYPES.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        FEATURES.register(modEventBus);
        STRUCTURE_TYPES.register(modEventBus);
        STRUCTURE_PIECE_TYPES.register(modEventBus);
        STRUCTURE_PROCESSORS.register(modEventBus);
        PLACEMENT_MODIFIER_TYPES.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        STRUCTURE_MODIFIERS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        // Register sub-registries
        BlockReg.register(modEventBus);
        ItemReg.register(modEventBus);
        BannerPatternReg.register(modEventBus);
        PotionReg.register(modEventBus);
        LootModifierReg.register(modEventBus);
        MobEffectReg.register(modEventBus);
        EnchantmentReg.register(modEventBus);
        EntityReg.register(modEventBus);
        BlockEntityReg.register(modEventBus);
        RecipeReg.register(modEventBus);
        ParticleReg.register(modEventBus);
        StructureReg.register(modEventBus);
        StructureProcessorReg.register(modEventBus);
        FeatureReg.register(modEventBus);
        PlacementTypeReg.register(modEventBus);
        StructureModifierReg.register(modEventBus);
    }


    public static final class BlockReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
            // register blocks and items together
            registerBlockPolishedEtc("limestone", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F));
            registerBlockPolishedEtc("marble", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(1.5F, 6.0F));
            registerBlockPolishedChiseledAndBricks("cretan_stone", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.CLAY).requiresCorrectToolForDrops().strength(80.0F, 3600.0F));
            registerLogsPlanksEtc("olive", 2.0F, 3.0F, MapColor.WOOD, MapColor.SAND, 5, 5, 20);
            registerLogsPlanksEtc("pomegranate", 2.2F, 3.0F, MapColor.TERRACOTTA_PURPLE, MapColor.CRIMSON_STEM, 0, 0, 0);
            registerLeaves("olive", 30, 60);
            registerLeaves("pomegranate", 0, 0);
            registerLeaves("golden", 30, 60);
            // register terracotta vase
            DeferredHolder<Block, Block> COLORLESS_VASE = BLOCKS.register("terracotta_vase", () ->
                    new VaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)
                            .strength(0.5F, 1.0F).noOcclusion()));
            GFRegistry.ItemReg.registerItemBlock(COLORLESS_VASE);
            // register colored terracotta vases
            for (DyeColor dyeColor : DyeColor.values()) {
                DeferredHolder<Block, Block> VASE = BLOCKS.register(dyeColor.getSerializedName() + "_terracotta_vase", () ->
                        new VaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(dyeColor.getMapColor())
                                .strength(0.5F, 1.0F).noOcclusion()));
                GFRegistry.ItemReg.registerItemBlock(VASE);
            }
        }

        public static final DeferredHolder<Block, Block> LIMESTONE = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "limestone"));
        public static final DeferredHolder<Block, Block> MARBLE = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "marble"));
        public static final DeferredHolder<Block, Block> OLIVE_LOG = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "olive_log"));
        public static final DeferredHolder<Block, Block> POMEGRANATE_LOG = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "pomegranate_log"));
        public static final DeferredHolder<Block, Block> OLIVE_LEAVES = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "olive_leaves"));
        public static final DeferredHolder<Block, Block> POMEGRANATE_LEAVES = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "pomegranate_leaves"));
        public static final DeferredHolder<Block, Block> GOLDEN_LEAVES = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "golden_leaves"));
        public static final DeferredHolder<Block, Block> CRETAN_STONE_BRICK = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "cretan_stone_brick"));
        public static final DeferredHolder<Block, Block> POLISHED_CRETAN_STONE = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "polished_cretan_stone"));
        public static final DeferredHolder<Block, Block> CRACKED_CRETAN_STONE_BRICK = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "cracked_cretan_stone_brick"));
        public static final DeferredHolder<Block, Block> CRACKED_POLISHED_CRETAN_STONE = DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "cracked_polished_cretan_stone"));
        public static final DeferredHolder<Block, Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () ->
                new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN)
                        .requiresCorrectToolForDrops().strength(3.0F, 6.0F)
                        .sound(SoundType.METAL)));
        public static final DeferredHolder<Block, Block> ICHOR_INFUSED_GEARBOX = BLOCKS.register("ichor_infused_gearbox", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_ORANGE)
                        .requiresCorrectToolForDrops().strength(3.0F, 6.0F)
                        .sound(SoundType.METAL)));
        public static final DeferredHolder<Block, Block> MYSTERIOUS_BOX = BLOCKS.register("mysterious_box", () ->
                new MysteriousBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD)
                        .strength(0.8F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
        public static final DeferredHolder<Block, Block> GIGANTE_HEAD = BLOCKS.register("gigante_head", () ->
                new MobHeadBlock(BlockEntityReg.GIGANTE_HEAD, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CACTUS).strength(1.0F).noOcclusion()));
        public static final DeferredHolder<Block, Block> ORTHUS_HEAD = BLOCKS.register("orthus_head", () ->
                new OrthusHeadBlock(BlockEntityReg.ORTHUS_HEAD, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CACTUS).strength(1.0F).noOcclusion()));
        public static final DeferredHolder<Block, Block> CERBERUS_HEAD = BLOCKS.register("cerberus_head", () ->
                new CerberusHeadBlock(BlockEntityReg.CERBERUS_HEAD, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CACTUS).strength(1.0F).noOcclusion()));
        public static final DeferredHolder<Block, Block> OIL_LAMP = BLOCKS.register("oil_lamp", () ->
                new OilLampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BROWN)
                        .noOcclusion().lightLevel(b -> b.getValue(OilLampBlock.LIT) ? 11 : 0).strength(0.2F, 0.1F)));
        public static final DeferredHolder<Block, Block> OLIVE_OIL = BLOCKS.register("olive_oil", () ->
                new OliveOilBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE)
                        .noOcclusion().noCollission().instabreak()
                        .randomTicks().lightLevel(b -> b.getValue(OliveOilBlock.LIT) ? 11 : 0).sound(SoundType.WET_GRASS)));
        public static final DeferredHolder<Block, Block> GOLDEN_STRING = BLOCKS.register("golden_string", () ->
                new GoldenStringBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CACTUS)
                        .lightLevel(b -> 8).instabreak().noCollission().noOcclusion().sound(SoundType.WOOL)));
        public static final DeferredHolder<Block, Block> OLIVE_SAPLING = BLOCKS.register("olive_sapling", () ->
                new SaplingBlock(OliveTreeGrower.GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
                        .noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        public static final DeferredHolder<Block, Block> POMEGRANATE_SAPLING = BLOCKS.register("pomegranate_sapling", () ->
                new PomegranateSaplingBlock(PomegranateTreeGrower.GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
                        .noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        public static final DeferredHolder<Block, Block> GOLDEN_SAPLING = BLOCKS.register("golden_sapling", () ->
                new SaplingBlock(GoldenTreeGrower.GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
                        .noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        public static final DeferredHolder<Block, Block> NEST = BLOCKS.register("nest", () ->
                new NestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_BROWN)
                        .strength(0.5F).sound(SoundType.GRASS)
                        .hasPostProcess((s, r, p) -> true).noOcclusion()));
        public static final DeferredHolder<Block, Block> WILD_ROSE = BLOCKS.register("wild_rose", () ->
                new WildRoseBlock(MobEffects.SATURATION, 9, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollission().instabreak().sound(SoundType.GRASS)));
        public static final DeferredHolder<Block, Block> REEDS = BLOCKS.register("reeds", () ->
                new ReedsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEAGRASS).noCollission().instabreak()
                        .offsetType(BlockBehaviour.OffsetType.XZ).randomTicks().sound(SoundType.CROP)));
        public static final DeferredHolder<Block, Block> LIGHT = BLOCKS.register("light", () ->
                new LightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT)));

        /**
         * Registers all of the following: log, stripped log, wood, stripped wood, planks, stairs, slab, door, trapdoor
         *
         * @param registryName       the base registry name
         * @param strength           the destroy time
         * @param hardness           the explosion resistance
         * @param side               the material color of the side
         * @param top                the material color of the top
         * @param fireSpread         the fire spread chance. The higher the number returned, the faster fire will spread around this block.
         * @param logFlammability    Chance that fire will spread and consume the log. 300 being a 100% chance, 0, being a 0% chance.
         * @param planksFlammability Chance that fire will spread and consume the plank. 300 being a 100% chance, 0, being a 0% chance.
         */
        private static void registerLogsPlanksEtc(final String registryName, final float strength, final float hardness,
                                                  final MapColor side, final MapColor top,
                                                  final int fireSpread, final int logFlammability, final int planksFlammability) {
            // create properties to apply to wood and non-rotatable variants
            // 1.21: Properties.copy -> Properties.ofFullCopy
            final BlockBehaviour.Properties woodProperties = BlockBehaviour.Properties
                    .ofFullCopy(Blocks.ACACIA_WOOD).mapColor(side)
                    .strength(strength, hardness).sound(SoundType.WOOD);
            // create properties to apply to log (multiple material colors)
            final BlockBehaviour.Properties logProperties = BlockBehaviour.Properties
                    .ofFullCopy(Blocks.ACACIA_WOOD).mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : side)
                    .strength(strength, hardness).sound(SoundType.WOOD);
            // create properties to apply to doors and trapdoors
            final Block.Properties doorProperties = BlockBehaviour.Properties
                    .ofFullCopy(Blocks.ACACIA_WOOD).mapColor(side)
                    .strength(strength, hardness).sound(SoundType.WOOD)
                    .noOcclusion().isValidSpawn((b, i, p, a) -> false);

            // register blocks
            final DeferredHolder<Block, Block> strippedLog = BLOCKS.register("stripped_" + registryName + "_log", () ->
                    new RotatedPillarBlock(woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return logFlammability;
                        }
                    }
            );
            final DeferredHolder<Block, Block> strippedWood = BLOCKS.register("stripped_" + registryName + "_wood", () ->
                    new RotatedPillarBlock(woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return logFlammability;
                        }
                    }
            );
            final DeferredHolder<Block, Block> log = BLOCKS.register(registryName + "_log", () ->
                    new RotatedPillarBlock(logProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return logFlammability;
                        }

                        @Override
                        public BlockState getToolModifiedState(BlockState state, UseOnContext context, net.neoforged.neoforge.common.ItemAbility toolAction, boolean simulate) {
                            if (toolAction == ItemAbilities.AXE_STRIP) {
                                return strippedLog.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
                            }
                            return super.getToolModifiedState(state, context, toolAction, simulate);
                        }
                    }
            );
            final DeferredHolder<Block, Block> wood = BLOCKS.register(registryName + "_wood", () ->
                    new RotatedPillarBlock(woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return logFlammability;
                        }

                        @Override
                        public BlockState getToolModifiedState(BlockState state, UseOnContext context, net.neoforged.neoforge.common.ItemAbility toolAction, boolean simulate) {
                            if (toolAction == ItemAbilities.AXE_STRIP) {
                                return strippedWood.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
                            }
                            return super.getToolModifiedState(state, context, toolAction, simulate);
                        }
                    }
            );
            final DeferredHolder<Block, Block> planks = BLOCKS.register(registryName + "_planks", () ->
                    new Block(woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return planksFlammability;
                        }
                    }
            );
            final DeferredHolder<Block, Block> slab = BLOCKS.register(registryName + "_slab", () ->
                    new SlabBlock(woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return planksFlammability;
                        }
                    }
            );
            final DeferredHolder<Block, Block> stairs = BLOCKS.register(registryName + "_stairs", () ->
                    new StairBlock(planks.get().defaultBlockState(), woodProperties) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return planksFlammability;
                        }
                    }
            );
            // 1.21: DoorBlock and TrapDoorBlock constructor signature changed - BlockSetType comes first
            final DeferredHolder<Block, Block> door = BLOCKS.register(registryName + "_door", () -> new DoorBlock(BlockSetType.OAK, doorProperties));
            final DeferredHolder<Block, Block> trapdoor = BLOCKS.register(registryName + "_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, doorProperties));
            // block items
            GFRegistry.ItemReg.registerItemBlock(log);
            GFRegistry.ItemReg.registerItemBlock(strippedLog);
            GFRegistry.ItemReg.registerItemBlock(wood);
            GFRegistry.ItemReg.registerItemBlock(strippedWood);
            GFRegistry.ItemReg.registerItemBlock(planks);
            GFRegistry.ItemReg.registerItemBlock(slab);
            GFRegistry.ItemReg.registerItemBlock(stairs);
            GFRegistry.ItemReg.registerItemBlock(door);
            GFRegistry.ItemReg.registerItemBlock(trapdoor);
        }

        /**
         * Registers a leaves block.
         *
         * @param registryName the base registry name
         * @param fireSpread   the fire spread chance. The higher the number returned, the faster fire will spread around this block.
         * @param flammability Chance that fire will spread and consume the block. 300 being a 100% chance, 0, being a 0% chance.
         */
        private static void registerLeaves(final String registryName, final int fireSpread, final int flammability) {
            // 1.21: Block.Properties.copy -> ofFullCopy
            final DeferredHolder<Block, Block> leaves = BLOCKS.register(registryName + "_leaves", () ->
                    new LeavesBlock(Block.Properties
                            .ofFullCopy(Blocks.ACACIA_LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                            .noOcclusion().isValidSpawn(GFRegistry.BlockReg::allowsSpawnOnLeaves).isSuffocating((s, r, p) -> false)
                            .isViewBlocking((s, r, p) -> false)) {
                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return fireSpread;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                            return flammability;
                        }
                    }
            );
            // block items
            GFRegistry.ItemReg.registerItemBlock(leaves);
        }

        /**
         * Registers the following: block, slab, stairs, pillar, polished block, polished slab, polished stairs
         *
         * @param registryName the base registry name.
         * @param properties   the block properties
         */
        private static void registerBlockPolishedEtc(final String registryName, final Block.Properties properties) {
            // raw, slab, and stairs
            final DeferredHolder<Block, Block> raw = BLOCKS.register(registryName, () -> new Block(properties));
            final DeferredHolder<Block, Block> slab = BLOCKS.register(registryName + "_slab", () -> new SlabBlock(properties));
            final DeferredHolder<Block, Block> stairs = BLOCKS.register(registryName + "_stairs", () -> new StairBlock(raw.get().defaultBlockState(), properties));
            // polished, slab, and stairs
            final DeferredHolder<Block, Block> polished = BLOCKS.register("polished_" + registryName, () -> new Block(properties));
            final DeferredHolder<Block, Block> polishedSlab = BLOCKS.register("polished_" + registryName + "_slab", () -> new SlabBlock(properties));
            final DeferredHolder<Block, Block> polishedStairs = BLOCKS.register("polished_" + registryName + "_stairs", () -> new StairBlock(polished.get().defaultBlockState(), properties));
            // pillar
            final DeferredHolder<Block, Block> pillar = BLOCKS.register(registryName + "_pillar", () -> new PillarBlock(properties));
            // block items
            GFRegistry.ItemReg.registerItemBlock(raw);
            GFRegistry.ItemReg.registerItemBlock(slab);
            GFRegistry.ItemReg.registerItemBlock(stairs);
            GFRegistry.ItemReg.registerItemBlock(polished);
            GFRegistry.ItemReg.registerItemBlock(polishedSlab);
            GFRegistry.ItemReg.registerItemBlock(polishedStairs);
            GFRegistry.ItemReg.registerItemBlock(pillar);
        }

        /**
         * Registers the following: block, chiseled, polished, cracked polished, brick, chiseled brick, cracked brick
         *
         * @param registryName the base registry name
         * @param properties   the block properties
         */
        private static void registerBlockPolishedChiseledAndBricks(final String registryName, final Block.Properties properties) {
            // raw, polished, chiseled, brick, and chiseled_brick
            ItemReg.registerItemBlock(BLOCKS.register(registryName, () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register("chiseled_" + registryName, () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register("polished_" + registryName, () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register("cracked_polished_" + registryName, () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register(registryName + "_brick", () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register("chiseled_" + registryName + "_brick", () -> new Block(properties)));
            ItemReg.registerItemBlock(BLOCKS.register("cracked_" + registryName + "_brick", () -> new Block(properties)));
        }

        private static Boolean allowsSpawnOnLeaves(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
            return entity == EntityType.OCELOT || entity == EntityType.PARROT; // || entity == EntityReg.DRYAD_ENTITY || entity == EntityReg.LAMPAD_ENTITY;
        }

    }

    public static final class ItemReg {


        private static final FoodProperties OLIVES_FOOD = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();
        private static final FoodProperties POMEGRANATE_FOOD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F)
                .effect(() -> new MobEffectInstance(MobEffectReg.PRISONER_OF_HADES, 6000), 1.0F).build();
        private static final FoodProperties AMBROSIA_FOOD = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2F).alwaysEdible()
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 0), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).build();
        private static final FoodProperties OLIVE_SALVE_FOOD = new FoodProperties.Builder().alwaysEdible().build();
        private static final FoodProperties PINECONE_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.0125F).build();

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
            modEventBus.addListener(GFRegistry.ItemReg::registerComposterRecipes);
        }

        private static void registerComposterRecipes(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                ComposterBlock.COMPOSTABLES.put(GOLDEN_SAPLING.get().asItem(), 0.3F);
                ComposterBlock.COMPOSTABLES.put(OLIVES.get().asItem(), 0.3F);
                ComposterBlock.COMPOSTABLES.put(PINECONE.get().asItem(), 0.3F);
                ComposterBlock.COMPOSTABLES.put(POMEGRANATE_SAPLING.get().asItem(), 0.3F);
                ComposterBlock.COMPOSTABLES.put(POMEGRANATE.get().asItem(), 0.65F);
                ComposterBlock.COMPOSTABLES.put(REEDS.get().asItem(), 0.5F);
                ComposterBlock.COMPOSTABLES.put(WILD_ROSE.get().asItem(), 0.85F);
            });
        }

        /// / LEGENDARY WEAPONS ////
        public static final DeferredHolder<Item, Item> THUNDERBOLT = ITEMS.register("thunderbolt", () ->
                new ThunderboltItem(new Item.Properties().rarity(Rarity.UNCOMMON).durability(170)));
        public static final DeferredHolder<Item, Item> WAND_OF_CIRCE = ITEMS.register("wand_of_circe", () ->
                new WandOfCirceItem(new Item.Properties().rarity(Rarity.RARE).durability(54)));
        public static final DeferredHolder<Item, Item> AVERNAL_BOW = ITEMS.register("avernal_bow", () ->
                new EnchantedBowItem.AvernalBowItem(new Item.Properties().rarity(Rarity.UNCOMMON).durability(384).stacksTo(1)));
        public static final DeferredHolder<Item, Item> APOLLO_BOW = ITEMS.register("apollo_bow", () ->
                new EnchantedBowItem.ApolloBowItem(new Item.Properties().rarity(Rarity.EPIC).durability(434).stacksTo(1)));
        public static final DeferredHolder<Item, Item> ARTEMIS_BOW = ITEMS.register("artemis_bow", () ->
                new EnchantedBowItem.ArtemisBowItem(new Item.Properties().rarity(Rarity.EPIC).durability(434).stacksTo(1)));

        /// / WEAPONS ////
        public static final DeferredHolder<Item, Item> WOODEN_CLUB = ITEMS.register("wooden_club", () ->
                new ClubItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> STONE_CLUB = ITEMS.register("stone_club", () ->
                new ClubItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> IRON_CLUB = ITEMS.register("iron_club", () ->
                new ClubItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> BIDENT = ITEMS.register("bident", () ->
                new BidentItem(Tiers.DIAMOND, new Item.Properties().rarity(Rarity.UNCOMMON).setNoRepair().stacksTo(1)));
        public static final DeferredHolder<Item, Item> WOODEN_SPEAR = ITEMS.register("wooden_spear", () ->
                new SpearItem(Tiers.WOOD, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> FLINT_SPEAR = ITEMS.register("flint_spear", () ->
                new SpearItem(GFTiers.FLINT, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> STONE_SPEAR = ITEMS.register("stone_spear", () ->
                new SpearItem(Tiers.STONE, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> IRON_SPEAR = ITEMS.register("iron_spear", () ->
                new SpearItem(Tiers.IRON, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> GOLDEN_SPEAR = ITEMS.register("golden_spear", () ->
                new SpearItem(Tiers.GOLD, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear", () ->
                new SpearItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear", () ->
                new SpearItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> FLINT_KNIFE = ITEMS.register("flint_knife", () ->
                new KnifeItem(GFTiers.FLINT, 3, -1.7F, -1.0F, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> IVORY_SWORD = ITEMS.register("ivory_sword", () ->
                new IvorySwordItem(GFTiers.IVORY, 3, -2.2F, new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> THROWING_AXE = ITEMS.register("throwing_axe", () ->
                new ThrowingAxeItem(Tiers.IRON, 6.0F, -3.1F, new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
        public static final DeferredHolder<Item, Item> DRAGON_TOOTH_ROD = ITEMS.register("dragon_tooth_rod", () ->
                new DragonToothRodItem(new Item.Properties().rarity(Rarity.EPIC).durability(128)));
        public static final DeferredHolder<Item, Item> DISCUS = ITEMS.register("discus", () ->
                new DiscusItem(new Item.Properties().stacksTo(16)));
        public static final DeferredHolder<Item, Item> GREEK_FIRE = ITEMS.register("greek_fire", () ->
                new GreekFireItem(new Item.Properties().stacksTo(16)));
        public static final DeferredHolder<Item, Item> WEB_BALL = ITEMS.register("web_ball", () ->
                new WebBallItem(new Item.Properties().stacksTo(16)));

        /// / LEGENDARY TOOLS AND ITEMS ////
        public static final DeferredHolder<Item, Item> BRONZE_FEATHER = ITEMS.register("bronze_feather", () ->
                new BronzeFeatherItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
        public static final DeferredHolder<Item, Item> DRAGON_TOOTH = ITEMS.register("dragon_tooth", () ->
                new DragonToothItem(new Item.Properties().rarity(Rarity.RARE)));
        public static final DeferredHolder<Item, Item> MIRROR = ITEMS.register("mirror", () ->
                new Item(new Item.Properties().stacksTo(1)));
        public static final DeferredHolder<Item, Item> CONCH = ITEMS.register("conch", () ->
                new ConchItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).durability(64)));
        public static final DeferredHolder<Item, Item> UNICORN_HORN = ITEMS.register("unicorn_horn", () ->
                new UnicornHornItem(new Item.Properties().rarity(Rarity.UNCOMMON).durability(44)));
        public static final DeferredHolder<Item, Item> HEART_OF_TALOS = ITEMS.register("heart_of_talos", () ->
                new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));
        public static final DeferredHolder<Item, Item> BAG_OF_WIND = ITEMS.register("bag_of_wind", () ->
                new BagOfWindItem(new Item.Properties().rarity(Rarity.RARE).durability(24)));
        public static final DeferredHolder<Item, Item> STAFF_OF_HEALING = ITEMS.register("staff_of_healing", () ->
                new StaffOfHealingItem(new Item.Properties().rarity(Rarity.RARE).durability(384)));
        public static final DeferredHolder<Item, Item> THYRSUS = ITEMS.register("thyrsus", () ->
                new ThyrsusItem(GFTiers.THYRSUS, 2.5F, -2.2F, new Item.Properties()));
        public static final DeferredHolder<Item, Item> AMBROSIA = ITEMS.register("ambrosia", () ->
                new HasCraftRemainderItem(ItemReg.HORN, new Item.Properties().food(AMBROSIA_FOOD).rarity(Rarity.EPIC)));
        public static final DeferredHolder<Item, Item> HORN_OF_PLENTY = ITEMS.register("horn_of_plenty", () ->
                new HornOfPlentyItem(ItemReg.HORN, new Item.Properties().durability(24).rarity(Rarity.UNCOMMON)));
        public static final DeferredHolder<Item, Item> GOLDEN_FLEECE = ITEMS.register("golden_fleece", () ->
                new Item(new Item.Properties().rarity(Rarity.RARE)));
        public static final DeferredHolder<Item, Item> GOLDEN_BALL = ITEMS.register("golden_ball", () ->
                new GoldenBallItem(new Item.Properties().rarity(Rarity.UNCOMMON).durability(680)));
        public static final DeferredHolder<Item, Item> ICHOR = ITEMS.register("ichor", () ->
                new Item(new Item.Properties().rarity(Rarity.RARE)) {
                    @Override
                    public boolean isFoil(ItemStack stack) {
                        return true;
                    }
                });
        public static final DeferredHolder<Item, Item> SPIDER_BANNER_PATTERN = ITEMS.register("spider_banner_pattern", () ->
                new BannerPatternItem(TagKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "pattern_item/spider")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

        /// / LEGENDARY ARMOR ////
        public static final DeferredHolder<Item, Item> HELM_OF_DARKNESS = ITEMS.register("helm_of_darkness", () ->
                new HelmOfDarknessItem(GFArmorMaterials.AVERNAL, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
        public static final DeferredHolder<Item, Item> WINGED_SANDALS = ITEMS.register("winged_sandals", () ->
                new WingedSandalsItem(GFArmorMaterials.WINGED, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
        public static final DeferredHolder<Item, Item> NEMEAN_LION_HIDE = ITEMS.register("nemean_lion_hide", () ->
                new NemeanLionHideItem(GFArmorMaterials.NEMEAN, ArmorItem.Type.HELMET,
                        new Item.Properties().rarity(Rarity.RARE).setNoRepair().stacksTo(1)));

        /// / ARMOR ////
        public static final DeferredHolder<Item, Item> HELLENIC_HELMET = ITEMS.register("hellenic_helmet", () ->
                new HellenicArmorItem(GFArmorMaterials.HELLENIC, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> HELLENIC_CHESTPLATE = ITEMS.register("hellenic_chestplate", () ->
                new HellenicArmorItem(GFArmorMaterials.HELLENIC, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> HELLENIC_LEGGINGS = ITEMS.register("hellenic_leggings", () ->
                new HellenicArmorItem(GFArmorMaterials.HELLENIC, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> HELLENIC_BOOTS = ITEMS.register("hellenic_boots", () ->
                new HellenicArmorItem(GFArmorMaterials.HELLENIC, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> SNAKESKIN_HELMET = ITEMS.register("snakeskin_helmet", () ->
                new SnakeskinArmorItem(GFArmorMaterials.SNAKESKIN, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> SNAKESKIN_CHESTPLATE = ITEMS.register("snakeskin_chestplate", () ->
                new SnakeskinArmorItem(GFArmorMaterials.SNAKESKIN, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> SNAKESKIN_LEGGINGS = ITEMS.register("snakeskin_leggings", () ->
                new SnakeskinArmorItem(GFArmorMaterials.SNAKESKIN, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        public static final DeferredHolder<Item, Item> SNAKESKIN_BOOTS = ITEMS.register("snakeskin_boots", () ->
                new SnakeskinArmorItem(GFArmorMaterials.SNAKESKIN, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

        /// / MISC ITEMS ////
        public static final DeferredHolder<Item, QuestItem> QUEST = ITEMS.register("quest", () ->
                new QuestItem(new Item.Properties()));
        public static final DeferredHolder<Item, InstrumentItem> PANFLUTE = ITEMS.register("panflute", () ->
                new InstrumentItem(new Item.Properties().stacksTo(1), () -> SoundEvents.NOTE_BLOCK_FLUTE.value()));
        public static final DeferredHolder<Item, InstrumentItem> WOODEN_LYRE = ITEMS.register("wooden_lyre", () ->
                new InstrumentItem(new Item.Properties().stacksTo(1), () -> SoundEvents.NOTE_BLOCK_HARP.value()));
        public static final DeferredHolder<Item, InstrumentItem> GOLDEN_LYRE = ITEMS.register("golden_lyre", () ->
                new InstrumentItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), () -> SoundEvents.NOTE_BLOCK_GUITAR.value()));
        public static final DeferredHolder<Item, Item> OLIVES = ITEMS.register("olives", () ->
                new Item(new Item.Properties().food(OLIVES_FOOD)));
        public static final DeferredHolder<Item, Item> OLIVE_OIL = ITEMS.register("olive_oil", () ->
                new OliveOilItem(BlockReg.OLIVE_OIL.get(), new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
        public static final DeferredHolder<Item, Item> OLIVE_SALVE = ITEMS.register("olive_salve", () ->
                new OliveSalveItem(new Item.Properties().stacksTo(1).food(OLIVE_SALVE_FOOD)));
        public static final DeferredHolder<Item, Item> POMEGRANATE = ITEMS.register("pomegranate", () ->
                new Item(new Item.Properties().food(POMEGRANATE_FOOD)));

        /// / CRAFTING MATERIALS ////
        public static final DeferredHolder<Item, Item> AVERNAL_FEATHER = ITEMS.register("avernal_feather", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> AVERNAL_HAIR = ITEMS.register("avernal_hair", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> AVERNAL_WING = ITEMS.register("avernal_wing", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> AVERNAL_HIDE = ITEMS.register("avernal_hide", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> AVERNAL_CLAW = ITEMS.register("avernal_claw", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> AVERNAL_SHARD = ITEMS.register("avernal_shard", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> ICHOR_INFUSED_GEAR = ITEMS.register("ichor_infused_gear", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> GOLDEN_STRING = ITEMS.register("golden_string", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> GORGON_BLOOD = ITEMS.register("gorgon_blood", () -> new GorgonBloodItem(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
        public static final DeferredHolder<Item, Item> BOAR_EAR = ITEMS.register("boar_ear", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BOAR_TUSK = ITEMS.register("boar_tusk", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> DEADLY_FANG = ITEMS.register("deadly_fang", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> GOLDEN_BRIDLE = ITEMS.register("golden_bridle", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> HORN = ITEMS.register("horn", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> PINECONE = ITEMS.register("pinecone", () -> new Item(new Item.Properties().food(PINECONE_FOOD)));
        public static final DeferredHolder<Item, Item> REEDS = ITEMS.register("reeds", () -> new BlockItem(BlockReg.REEDS.get(), new Item.Properties()));
        public static final DeferredHolder<Item, Item> SCYLLA_BONE = ITEMS.register("scylla_bone", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> SNAKESKIN = ITEMS.register("snakeskin", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> TOUGH_SNAKESKIN = ITEMS.register("tough_snakeskin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
        /// / BRONZE SCRAP ////
        public static final DeferredHolder<Item, Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_BOWL = ITEMS.register("bronze_bowl", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_COINS = ITEMS.register("bronze_coins", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_CUIRASS = ITEMS.register("bronze_cuirass", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_FIGURINE = ITEMS.register("bronze_figurine", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_GOBLET = ITEMS.register("bronze_goblet", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_SHIELD = ITEMS.register("bronze_shield", () -> new BronzeScrapItem(new Item.Properties()));
        public static final DeferredHolder<Item, Item> BRONZE_VASE = ITEMS.register("bronze_vase", () -> new BronzeScrapItem(new Item.Properties()));

        /// / SPAWN EGGS ////
        public static final DeferredHolder<Item, Item> ARA_SPAWN_EGG = ITEMS.register("ara_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.ARA, 0xffffff, 0xbbbbbb, new Item.Properties()));
        public static final DeferredHolder<Item, Item> ARACHNE_SPAWN_EGG = ITEMS.register("arachne_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.ARACHNE, 0x9c7b50, 0xa80e0e, new Item.Properties()));
        public static final DeferredHolder<Item, Item> ARION_SPAWN_EGG = ITEMS.register("arion_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.ARION, 0xdfc014, 0xb58614, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CENTAUR_SPAWN_EGG = ITEMS.register("centaur_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CENTAUR, 0x734933, 0x83251f, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CERASTES_SPAWN_EGG = ITEMS.register("cerastes_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CERASTES, 0x847758, 0x997c4d, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CIRCE_SPAWN_EGG = ITEMS.register("circe_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CIRCE, 0x844797, 0xe8c669, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CRETAN_MINOTAUR_SPAWN_EGG = ITEMS.register("cretan_minotaur_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CRETAN_MINOTAUR, 0x2a2a2a, 0x734933, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CYCLOPS_SPAWN_EGG = ITEMS.register("cyclops_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CYCLOPS, 0xda662c, 0x2c1e0e, new Item.Properties()));
        public static final DeferredHolder<Item, Item> CYPRIAN_SPAWN_EGG = ITEMS.register("cyprian_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.CYPRIAN, 0x443626, 0x83251f, new Item.Properties()));
        public static final DeferredHolder<Item, Item> DRAKAINA_SPAWN_EGG = ITEMS.register("drakaina_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.DRAKAINA, 0x724e36, 0x398046, new Item.Properties()));
        public static final DeferredHolder<Item, Item> DRYAD_SPAWN_EGG = ITEMS.register("dryad_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.DRYAD, 0x443626, 0xfed93f, new Item.Properties()));
        public static final DeferredHolder<Item, Item> ELPIS_SPAWN_EGG = ITEMS.register("elpis_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.ELPIS, 0xe7aae4, 0xeeeeee, new Item.Properties()));
        public static final DeferredHolder<Item, Item> EMPUSA_SPAWN_EGG = ITEMS.register("empusa_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.EMPUSA, 0x222222, 0x83251f, new Item.Properties()));
        public static final DeferredHolder<Item, Item> FURY_SPAWN_EGG = ITEMS.register("fury_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.FURY, 0xbd4444, 0x6c2426, new Item.Properties()));
        public static final DeferredHolder<Item, Item> GIANT_BOAR_SPAWN_EGG = ITEMS.register("giant_boar_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.GIANT_BOAR, 0x5b433a, 0xe8a074, new Item.Properties()));
        public static final DeferredHolder<Item, Item> GIGANTE_SPAWN_EGG = ITEMS.register("gigante_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.GIGANTE, 0xd3dba7, 0x6a602b, new Item.Properties()));
        public static final DeferredHolder<Item, Item> GOLDEN_RAM_SPAWN_EGG = ITEMS.register("golden_ram_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.GOLDEN_RAM, 0xdfc014, 0xd08d26, new Item.Properties()));
        public static final DeferredHolder<Item, Item> GORGON_SPAWN_EGG = ITEMS.register("gorgon_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.GORGON, 0x3a8228, 0xbcbcbc, new Item.Properties()));
        public static final DeferredHolder<Item, Item> HARPY_SPAWN_EGG = ITEMS.register("harpy_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.HARPY, 0x724e36, 0x332411, new Item.Properties()));
        public static final DeferredHolder<Item, Item> HYDRA_SPAWN_EGG = ITEMS.register("hydra_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.HYDRA, 0x372828, 0x9d4217, new Item.Properties()));
        public static final DeferredHolder<Item, Item> LAMPAD_SPAWN_EGG = ITEMS.register("lampad_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.LAMPAD, 0x643026, 0xfed93f, new Item.Properties()));
        public static final DeferredHolder<Item, Item> MAD_COW_SPAWN_EGG = ITEMS.register("mad_cow_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.MAD_COW, 0x443626, 0xcf9797, new Item.Properties()));
        public static final DeferredHolder<Item, Item> MAKHAI_SPAWN_EGG = ITEMS.register("makhai_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.MAKHAI, 0x513f38, 0xf33531, new Item.Properties()));
        public static final DeferredHolder<Item, Item> MINOTAUR_SPAWN_EGG = ITEMS.register("minotaur_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.MINOTAUR, 0x443626, 0x734933, new Item.Properties()));
        public static final DeferredHolder<Item, Item> NAIAD_SPAWN_EGG = ITEMS.register("naiad_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.NAIAD, 0x7caba1, 0xe67830, new Item.Properties()));
        public static final DeferredHolder<Item, Item> NEMEAN_LION_SPAWN_EGG = ITEMS.register("nemean_lion_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.NEMEAN_LION, 0xd08d26, 0x7d3107, new Item.Properties()));
        public static final DeferredHolder<Item, Item> ORTHUS_SPAWN_EGG = ITEMS.register("orthus_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.ORTHUS, 0x493569, 0xe42e2e, new Item.Properties()));
        public static final DeferredHolder<Item, Item> PEGASUS_SPAWN_EGG = ITEMS.register("pegasus_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.PEGASUS, 0x916535, 0xe8e8e8, new Item.Properties()));
        public static final DeferredHolder<Item, Item> PYTHON_SPAWN_EGG = ITEMS.register("python_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.PYTHON, 0x3a8228, 0x1e4c11, new Item.Properties()));
        public static final DeferredHolder<Item, Item> SATYR_SPAWN_EGG = ITEMS.register("satyr_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.SATYR, 0x54371d, 0xa16648, new Item.Properties()));
        public static final DeferredHolder<Item, Item> SHADE_SPAWN_EGG = ITEMS.register("shade_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.SHADE, 0x222222, 0x000000, new Item.Properties()));
        public static final DeferredHolder<Item, Item> SIREN_SPAWN_EGG = ITEMS.register("siren_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.SIREN, 0x729f92, 0x398046, new Item.Properties()));
        public static final DeferredHolder<Item, Item> STYMPHALIAN_SPAWN_EGG = ITEMS.register("stymphalian_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.STYMPHALIAN, 0x684822, 0xc08845, new Item.Properties()));
        public static final DeferredHolder<Item, Item> TRITON_SPAWN_EGG = ITEMS.register("triton_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.TRITON, 0x527f72, 0x398046, new Item.Properties()));
        public static final DeferredHolder<Item, Item> UNICORN_SPAWN_EGG = ITEMS.register("unicorn_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.UNICORN, 0xeeeeee, 0xe8e8e8, new Item.Properties()));
        public static final DeferredHolder<Item, Item> WHIRL_SPAWN_EGG = ITEMS.register("whirl_spawn_egg", () ->
                new DeferredSpawnEggItem(EntityReg.WHIRL, 0x1EF6FF, 0xededed, new Item.Properties()));

        // TODO spawn eggs for other bosses? bull, geryon, talos, cerberus, charybdis

        /// / LEGENDARY ITEM BLOCKS ////
        public static final DeferredHolder<Item, Item> PALLADIUM = ITEMS.register("palladium", () ->
                new PalladiumItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));

        /// / ITEM BLOCKS ////
        public static final DeferredHolder<Item, BlockItem> BRONZE_BLOCK = registerItemBlock(BlockReg.BRONZE_BLOCK);
        public static final DeferredHolder<Item, BlockItem> ICHOR_INFUSED_GEARBOX = registerItemBlock(BlockReg.ICHOR_INFUSED_GEARBOX, p -> p.rarity(Rarity.RARE));
        public static final DeferredHolder<Item, BlockItem> MYSTERIOUS_BOX = registerItemBlock(BlockReg.MYSTERIOUS_BOX, p -> p.rarity(Rarity.UNCOMMON));
        public static final DeferredHolder<Item, BlockItem> GIGANTE_HEAD = ITEMS.register("gigante_head", () -> new GiganteHeadItem(BlockReg.GIGANTE_HEAD.get(), new Item.Properties()));
        public static final DeferredHolder<Item, BlockItem> ORTHUS_HEAD = ITEMS.register("orthus_head", () -> new OrthusHeadItem(BlockReg.ORTHUS_HEAD.get(), new Item.Properties()));
        public static final DeferredHolder<Item, BlockItem> CERBERUS_HEAD = ITEMS.register("cerberus_head", () -> new CerberusHeadItem(BlockReg.CERBERUS_HEAD.get(), new Item.Properties()));
        public static final DeferredHolder<Item, BlockItem> OIL_LAMP = registerItemBlock(BlockReg.OIL_LAMP);
        public static final DeferredHolder<Item, BlockItem> OLIVE_SAPLING = registerItemBlock(BlockReg.OLIVE_SAPLING);
        public static final DeferredHolder<Item, BlockItem> POMEGRANATE_SAPLING = registerItemBlock(BlockReg.POMEGRANATE_SAPLING);
        public static final DeferredHolder<Item, BlockItem> GOLDEN_SAPLING = registerItemBlock(BlockReg.GOLDEN_SAPLING, p -> p.rarity(Rarity.UNCOMMON));
        public static final DeferredHolder<Item, BlockItem> WILD_ROSE = registerItemBlock(BlockReg.WILD_ROSE, p -> p.rarity(Rarity.UNCOMMON));
        public static final DeferredHolder<Item, BlockItem> NEST = registerItemBlock(BlockReg.NEST);

        /**
         * Registers an item for the given block
         *
         * @param blockSupplier the block supplier
         * @return the BlockItem registry object
         */
        private static DeferredHolder<Item, BlockItem> registerItemBlock(final DeferredHolder<Block, ? extends Block> blockSupplier) {
            return ITEMS.register(blockSupplier.getId().getPath(), itemBlock(blockSupplier, p -> {
            }));
        }

        /**
         * Registers an item for the given block
         *
         * @param blockSupplier the block supplier
         * @param consumer      the item properties consumer
         * @return the BlockItem registry object
         */
        private static DeferredHolder<Item, BlockItem> registerItemBlock(final DeferredHolder<Block, ? extends Block> blockSupplier, Consumer<Item.Properties> consumer) {
            return ITEMS.register(blockSupplier.getId().getPath(), itemBlock(blockSupplier, consumer));
        }

        /**
         * Creates a block item supplier for the given block
         *
         * @param blockSupplier the block supplier
         * @return a supplier for the block item
         */
        private static Supplier<BlockItem> itemBlock(final DeferredHolder<Block, ? extends Block> blockSupplier, Consumer<Item.Properties> consumer) {
            Item.Properties props = new Item.Properties();
            consumer.accept(props);
            return () -> new BlockItem(blockSupplier.get(), props);
        }



        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GROUP_SM = CREATIVE_TABS.register("greekfantasy", ()->CreativeModeTab.builder().title(Component.literal("Greek Fantasy")).icon(() -> new ItemStack(PANFLUTE.get())).displayItems((itemDisplayParameters, output) -> {
                    //// LEGENDARY WEAPONS ////
                    output.accept(THUNDERBOLT.get());
                    output.accept(WAND_OF_CIRCE.get());

                    // TODO 1.21: checkAndApplyBaseEnchantments requires Level access
                    // For now, just output the bows without pre-enchantment
                    output.accept(AVERNAL_BOW.get());
                    output.accept(APOLLO_BOW.get());
                    output.accept(ARTEMIS_BOW.get());


                    //// WEAPONS ////
                    output.accept(WOODEN_CLUB.get());

                    output.accept(STONE_CLUB.get());

                    output.accept(IRON_CLUB.get());

                    output.accept(BIDENT.get());

                    output.accept(WOODEN_SPEAR.get());

                    output.accept(FLINT_SPEAR.get());

                    output.accept(STONE_SPEAR.get());

                    output.accept(IRON_SPEAR.get());

                    output.accept(GOLDEN_SPEAR.get());

                    output.accept(DIAMOND_SPEAR.get());

                    output.accept(NETHERITE_SPEAR.get());

                    output.accept(FLINT_KNIFE.get());

                    output.accept(IVORY_SWORD.get());

                    output.accept(THROWING_AXE.get());

                    // Dragon Tooth Rod automatically gets Fishing Luck enchantment in inventoryTick()
                    output.accept(DRAGON_TOOTH_ROD.get());

                    output.accept(DISCUS.get());

                    output.accept(GREEK_FIRE.get());

                    output.accept(WEB_BALL.get());


                    //// LEGENDARY TOOLS AND ITEMS ////
                    output.accept(BRONZE_FEATHER.get());

                    output.accept(DRAGON_TOOTH.get());

                    output.accept(MIRROR.get());

                    output.accept(CONCH.get());

                    output.accept(UNICORN_HORN.get());

                    output.accept(HEART_OF_TALOS.get());

                    output.accept(BAG_OF_WIND.get());

                    output.accept(STAFF_OF_HEALING.get());

                    output.accept(THYRSUS.get());

                    output.accept(AMBROSIA.get());

                    output.accept(HORN_OF_PLENTY.get());

                    output.accept(GOLDEN_FLEECE.get());

                    output.accept(GOLDEN_BALL.get());

                    output.accept(ICHOR.get());

                    output.accept(SPIDER_BANNER_PATTERN.get());


                    //// LEGENDARY ARMOR ////
                    output.accept(HELM_OF_DARKNESS.get());

                    if (GreekFantasy.CONFIG.isOverstepEnabled()) {
                        ItemStack wingedSandlas = new ItemStack(WINGED_SANDALS.get());
                        output.accept(wingedSandlas);
                    }

                    output.accept(WINGED_SANDALS.get());
                    output.accept(NEMEAN_LION_HIDE.get());


                    //// ARMOR ////
                    output.accept(HELLENIC_HELMET.get());

                    output.accept(HELLENIC_CHESTPLATE.get());

                    output.accept(HELLENIC_LEGGINGS.get());

                    output.accept(HELLENIC_BOOTS.get());


                    if (GreekFantasy.CONFIG.isPoisoningEnabled()) {
                        ItemStack snakeskinHelmet = new ItemStack(SNAKESKIN_HELMET.get());
                        ItemStack snakeskinChestplate = new ItemStack(SNAKESKIN_CHESTPLATE.get());
                        ItemStack snakeskinLeggings = new ItemStack(SNAKESKIN_LEGGINGS.get());
                        ItemStack snakeskinBoots = new ItemStack(SNAKESKIN_BOOTS.get());

                        output.accept(snakeskinHelmet);

                        output.accept(snakeskinChestplate);

                        output.accept(snakeskinLeggings);

                        output.accept(snakeskinBoots);
                    }


                    output.accept(SNAKESKIN_HELMET.get());

                    output.accept(SNAKESKIN_CHESTPLATE.get());

                    output.accept(SNAKESKIN_LEGGINGS.get());

                    output.accept(SNAKESKIN_BOOTS.get());


                    //// MISC ITEMS ////
                    List<ResourceLocation> questIds = new ArrayList<>();
                    // add each non-disabled quest to the list
                    for (Map.Entry<ResourceLocation, Quest> entry : GreekFantasy.QUEST_MAP.entrySet()) {
                        if (!entry.getValue().isDisabled()) {
                            questIds.add(entry.getKey());
                        }
                    }
                    // sort by namespace and path
                    questIds.sort(ResourceLocation::compareNamespaced);
                    // add itemstack for each quest
                    for (ResourceLocation questId : questIds) {
                        ItemStack itemStack = new ItemStack(QUEST.get());
                        // 1.21: Properly create and set CustomData component
                        net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
                        tag.putString(KEY_QUEST, questId.toString());
                        itemStack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                                     net.minecraft.world.item.component.CustomData.of(tag));
                        output.accept(itemStack);
                    }


                    output.accept(PANFLUTE.get());

                    output.accept(WOODEN_LYRE.get());

                    output.accept(GOLDEN_LYRE.get());

                    output.accept(OLIVES.get());

                    output.accept(OLIVE_OIL.get());

                    output.accept(OLIVE_SALVE.get());

                    output.accept(POMEGRANATE.get());


                    //// CRAFTING MATERIALS ////
                    output.accept(AVERNAL_FEATHER.get());
                    output.accept(AVERNAL_HAIR.get());
                    output.accept(AVERNAL_WING.get());
                    output.accept(AVERNAL_HIDE.get());
                    output.accept(AVERNAL_CLAW.get());
                    output.accept(AVERNAL_SHARD.get());
                    output.accept(ICHOR_INFUSED_GEAR.get());
                    output.accept(GOLDEN_STRING.get());
                    output.accept(GORGON_BLOOD.get());
                    output.accept(BOAR_EAR.get());
                    output.accept(BOAR_TUSK.get());
                    output.accept(DEADLY_FANG.get());
                    output.accept(GOLDEN_BRIDLE.get());
                    output.accept(HORN.get());
                    output.accept(PINECONE.get());
                    output.accept(REEDS.get());
                    output.accept(SCYLLA_BONE.get());
                    output.accept(SNAKESKIN.get());
                    output.accept(TOUGH_SNAKESKIN.get());
                    //// BRONZE SCRAP ////
                    output.accept(BRONZE_INGOT.get());
                    output.accept(BRONZE_NUGGET.get());
                    output.accept(BRONZE_BOWL.get());
                    output.accept(BRONZE_COINS.get());
                    output.accept(BRONZE_CUIRASS.get());
                    output.accept(BRONZE_FIGURINE.get());
                    output.accept(BRONZE_GOBLET.get());
                    output.accept(BRONZE_HELMET.get());
                    output.accept(BRONZE_SHIELD.get());
                    output.accept(BRONZE_VASE.get());

                    //// SPAWN EGGS ////
                    output.accept(ARA_SPAWN_EGG.get());

                    output.accept(ARACHNE_SPAWN_EGG.get());

                    output.accept(ARION_SPAWN_EGG.get());

                    output.accept(CENTAUR_SPAWN_EGG.get());

                    output.accept(CERASTES_SPAWN_EGG.get());

                    output.accept(CIRCE_SPAWN_EGG.get());

                    output.accept(CRETAN_MINOTAUR_SPAWN_EGG.get());

                    output.accept(CYCLOPS_SPAWN_EGG.get());

                    output.accept(CYPRIAN_SPAWN_EGG.get());

                    output.accept(DRAKAINA_SPAWN_EGG.get());

                    output.accept(DRYAD_SPAWN_EGG.get());

                    output.accept(ELPIS_SPAWN_EGG.get());

                    output.accept(EMPUSA_SPAWN_EGG.get());

                    output.accept(FURY_SPAWN_EGG.get());

                    output.accept(GIANT_BOAR_SPAWN_EGG.get());

                    output.accept(GIGANTE_SPAWN_EGG.get());

                    output.accept(GOLDEN_RAM_SPAWN_EGG.get());

                    output.accept(GORGON_SPAWN_EGG.get());

                    output.accept(HARPY_SPAWN_EGG.get());

                    output.accept(HYDRA_SPAWN_EGG.get());

                    output.accept(LAMPAD_SPAWN_EGG.get());

                    output.accept(MAD_COW_SPAWN_EGG.get());

                    output.accept(MAKHAI_SPAWN_EGG.get());

                    output.accept(MINOTAUR_SPAWN_EGG.get());

                    output.accept(NAIAD_SPAWN_EGG.get());

                    output.accept(NEMEAN_LION_SPAWN_EGG.get());

                    output.accept(ORTHUS_SPAWN_EGG.get());

                    output.accept(PEGASUS_SPAWN_EGG.get());

                    output.accept(PYTHON_SPAWN_EGG.get());

                    output.accept(SATYR_SPAWN_EGG.get());

                    output.accept(SHADE_SPAWN_EGG.get());

                    output.accept(SIREN_SPAWN_EGG.get());

                    output.accept(STYMPHALIAN_SPAWN_EGG.get());

                    output.accept(TRITON_SPAWN_EGG.get());

                    output.accept(UNICORN_SPAWN_EGG.get());

                    output.accept(WHIRL_SPAWN_EGG.get());


                    output.accept(PALLADIUM.get());

                    // Building Blocks  
                    output.accept(BlockReg.LIMESTONE.get());
                    output.accept(BlockReg.MARBLE.get());
                    
                    // Wood Blocks
                    output.accept(BlockReg.OLIVE_LOG.get());
                    output.accept(BlockReg.OLIVE_LEAVES.get());
                    output.accept(BlockReg.POMEGRANATE_LOG.get());
                    output.accept(BlockReg.POMEGRANATE_LEAVES.get());
                    output.accept(BlockReg.GOLDEN_LEAVES.get());

                    output.accept(BRONZE_BLOCK.get());
                    output.accept(ICHOR_INFUSED_GEARBOX.get());
                    output.accept(MYSTERIOUS_BOX.get());
                    output.accept(GIGANTE_HEAD.get());
                    output.accept(ORTHUS_HEAD.get());
                    output.accept(CERBERUS_HEAD.get());
                    output.accept(OIL_LAMP.get());
                    output.accept(OLIVE_SAPLING.get());
                    output.accept(POMEGRANATE_SAPLING.get());
                    output.accept(GOLDEN_SAPLING.get());
                    output.accept(WILD_ROSE.get());
                    output.accept(NEST.get());

                }
        ).build());

    }

    public static final class BannerPatternReg {
        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        // 1.21: Banner patterns are now data-driven via JSON files
        // Pattern definitions go in data/greekfantasy/banner_pattern/*.json
        // Pattern tags go in data/greekfantasy/tags/banner_pattern/*.json
        // public static final DeferredHolder<BannerPattern, BannerPattern> SPIDER = BANNER_PATTERNS.register("spider", () ->
        //         new BannerPattern(ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "spi")));
    }

    public static final class EntityReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
            // event listeners
            modEventBus.addListener(GFRegistry.EntityReg::registerEntityAttributes);
        }

        private static void registerEntityAttributes(EntityAttributeCreationEvent event) {
            register(event, AUTOMATON.get(), Automaton::createAttributes, Mob::checkMobSpawnRules);
            register(event, ARA.get(), Ara::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, ARACHNE.get(), Arachne::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, ARION.get(), Arion::createAttributes, Mob::checkMobSpawnRules);
            register(event, BABY_SPIDER.get(), BabySpider::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, BRONZE_BULL.get(), BronzeBull::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, CENTAUR.get(), Centaur::createAttributes, Mob::checkMobSpawnRules);
            register(event, CERASTES.get(), Cerastes::createAttributes, Cerastes::checkCerastesSpawnRules);
            register(event, CERBERUS.get(), Cerberus::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, CHARYBDIS.get(), Charybdis::createAttributes, SpawnRulesUtil::checkWaterMonsterSpawnRules);
            register(event, CIRCE.get(), Circe::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, CRETAN_MINOTAUR.get(), CretanMinotaur::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, CYCLOPS.get(), Cyclops::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, CYPRIAN.get(), Cyprian::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, DRAKAINA.get(), Drakaina::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, DRYAD.get(), Dryad::createAttributes, Mob::checkMobSpawnRules);
            register(event, ELPIS.get(), Elpis::createAttributes, Mob::checkMobSpawnRules);
            register(event, EMPUSA.get(), Empusa::createAttributes, Empusa::checkEmpusaSpawnRules);
            register(event, FURY.get(), Fury::createAttributes, Monster::checkAnyLightMonsterSpawnRules);
            register(event, GERYON.get(), Geryon::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, GIANT_BOAR.get(), GiantBoar::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, GIGANTE.get(), Gigante::createAttributes, Mob::checkMobSpawnRules);
            register(event, GOLDEN_RAM.get(), GoldenRam::createAttributes, Mob::checkMobSpawnRules);
            register(event, GORGON.get(), Gorgon::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, HARPY.get(), Harpy::createAttributes, Monster::checkAnyLightMonsterSpawnRules);
            register(event, HYDRA.get(), Hydra::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, HYDRA_HEAD.get(), HydraHead::createAttributes, null);
            register(event, LAMPAD.get(), Lampad::createAttributes, Mob::checkMobSpawnRules);
            register(event, MAD_COW.get(), MadCow::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, MAKHAI.get(), Makhai::createAttributes, SpawnRulesUtil::checkAnyLightMonsterSpawnRules);
            register(event, MINOTAUR.get(), Minotaur::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, NAIAD.get(), Naiad::createAttributes, SpawnRulesUtil::checkWaterMobSpawnRules);
            register(event, NEMEAN_LION.get(), NemeanLion::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, ORTHUS.get(), Orthus::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            event.put(PALLADIUM.get(), Palladium.createAttributes().build());
            register(event, PEGASUS.get(), Pegasus::createAttributes, Mob::checkMobSpawnRules);
            register(event, PYTHON.get(), Python::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, SATYR.get(), Satyr::createAttributes, Mob::checkMobSpawnRules);
            register(event, SCYLLA.get(), Scylla::createAttributes, SpawnRulesUtil::checkWaterMonsterSpawnRules);
            register(event, SHADE.get(), Shade::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, SIREN.get(), Siren::createAttributes, Siren::checkSirenSpawnRules);
            register(event, SPARTI.get(), Sparti::createAttributes, Mob::checkMobSpawnRules);
            register(event, STYMPHALIAN.get(), Stymphalian::createAttributes, Monster::checkMonsterSpawnRules);
            register(event, TALOS.get(), Talos::createAttributes, SpawnRulesUtil::checkMonsterSpawnRules);
            register(event, TRITON.get(), Triton::createAttributes, SpawnRulesUtil::checkWaterMobSpawnRules);
            register(event, UNICORN.get(), Unicorn::createAttributes, Mob::checkMobSpawnRules);
            register(event, WHIRL.get(), Whirl::createAttributes, SpawnRulesUtil::checkWaterMobSpawnRules);
        }

        /**
         * Helper method to register mob entity attributes and placement predicates at the same time.
         *
         * @param event              the entity attribute creation event
         * @param entityType         the entity type
         * @param attributeSupplier  a supplier to the attribute builder
         * @param placementPredicate the placement predicate, can be null
         * @param <T>                a mob entity
         */
        private static <T extends Mob> void register(final EntityAttributeCreationEvent event, final EntityType<T> entityType,
                                                     Supplier<AttributeSupplier.Builder> attributeSupplier,
                                                     @Nullable final SpawnPlacements.SpawnPredicate<T> placementPredicate) {
            // register attributes
            event.put(entityType, attributeSupplier.get().build());
            // 1.21: SpawnPlacements.register is now done via RegisterSpawnPlacementsEvent
            // Placement predicates are stored and registered in onRegisterSpawnPlacements
        }

        // Store placement predicates to register later via event
        private static final java.util.Map<EntityType<?>, SpawnPlacements.SpawnPredicate<?>> PLACEMENT_PREDICATES = new java.util.HashMap<>();

        public static void onRegisterSpawnPlacements(net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent event) {
            // 1.21: SpawnPlacements use SpawnPlacementTypes instead of SpawnPlacements.Type
            // This method should be called from main mod class via event listener
            // For now, placements need to be registered manually or via data
        }

        // creature
        public static final DeferredHolder<EntityType<?>, EntityType<Automaton>> AUTOMATON = ENTITY_TYPES.register("automaton", () ->
                EntityType.Builder.of(Automaton::new, MobCategory.MISC)
                        .sized(0.94F, 2.48F)
                        .build("automaton"));
        public static final DeferredHolder<EntityType<?>, EntityType<Ara>> ARA = ENTITY_TYPES.register("ara", () ->
                EntityType.Builder.of(Ara::new, MobCategory.MONSTER)
                        .sized(0.67F, 1.8F)
                        .build("ara"));
        public static final DeferredHolder<EntityType<?>, EntityType<Arachne>> ARACHNE = ENTITY_TYPES.register("arachne", () ->
                EntityType.Builder.of(Arachne::new, MobCategory.MONSTER)
                        .sized(0.94F, 1.9F)
                        .build("arachne"));
        public static final DeferredHolder<EntityType<?>, EntityType<Arion>> ARION = ENTITY_TYPES.register("arion", () ->
                EntityType.Builder.of(Arion::new, MobCategory.CREATURE)
                        .sized(1.39F, 1.98F)
                        .build("arion"));
        public static final DeferredHolder<EntityType<?>, EntityType<BabySpider>> BABY_SPIDER = ENTITY_TYPES.register("baby_spider", () ->
                EntityType.Builder.of(BabySpider::new, MobCategory.MONSTER)
                        .sized(0.5F, 0.65F)
                        .build("baby_spider"));
        public static final DeferredHolder<EntityType<?>, EntityType<BronzeBull>> BRONZE_BULL = ENTITY_TYPES.register("bronze_bull", () ->
                EntityType.Builder.of(BronzeBull::new, MobCategory.MONSTER)
                        .sized(1.95F, 2.98F).fireImmune()
                        .build("bronze_bull"));
        public static final DeferredHolder<EntityType<?>, EntityType<Centaur>> CENTAUR = ENTITY_TYPES.register("centaur", () ->
                EntityType.Builder.of(Centaur::new, MobCategory.CREATURE)
                        .sized(1.39F, 2.49F)
                        .build("centaur"));
        public static final DeferredHolder<EntityType<?>, EntityType<Cerastes>> CERASTES = ENTITY_TYPES.register("cerastes", () ->
                EntityType.Builder.of(Cerastes::new, MobCategory.CREATURE)
                        .sized(0.98F, 0.94F)
                        .build("cerastes"));
        public static final DeferredHolder<EntityType<?>, EntityType<Cerberus>> CERBERUS = ENTITY_TYPES.register("cerberus", () ->
                EntityType.Builder.of(Cerberus::new, MobCategory.MONSTER)
                        .sized(1.98F, 1.9F).fireImmune()
                        .build("cerberus"));
        public static final DeferredHolder<EntityType<?>, EntityType<Charybdis>> CHARYBDIS = ENTITY_TYPES.register("charybdis", () ->
                EntityType.Builder.of(Charybdis::new, MobCategory.WATER_CREATURE)
                        .sized(5.9F, 7.9F).fireImmune()
                        .build("charybdis"));
        public static final DeferredHolder<EntityType<?>, EntityType<Circe>> CIRCE = ENTITY_TYPES.register("circe", () ->
                EntityType.Builder.of(Circe::new, MobCategory.MONSTER)
                        .sized(0.67F, 1.8F)
                        .build("circe"));
        public static final DeferredHolder<EntityType<?>, EntityType<CretanMinotaur>> CRETAN_MINOTAUR = ENTITY_TYPES.register("cretan_minotaur", () ->
                EntityType.Builder.of(CretanMinotaur::new, MobCategory.MONSTER)
                        .sized(0.989F, 3.395F).fireImmune()
                        .build("cretan_minotaur"));
        public static final DeferredHolder<EntityType<?>, EntityType<Cyclops>> CYCLOPS = ENTITY_TYPES.register("cyclops", () ->
                EntityType.Builder.of(Cyclops::new, MobCategory.MONSTER)
                        .sized(0.99F, 2.92F)
                        .build("cyclops"));
        public static final DeferredHolder<EntityType<?>, EntityType<Cyprian>> CYPRIAN = ENTITY_TYPES.register("cyprian", () ->
                EntityType.Builder.of(Cyprian::new, MobCategory.MONSTER)
                        .sized(1.39F, 2.49F)
                        .build("cyprian"));
        public static final DeferredHolder<EntityType<?>, EntityType<Drakaina>> DRAKAINA = ENTITY_TYPES.register("drakaina", () ->
                EntityType.Builder.of(Drakaina::new, MobCategory.MONSTER)
                        .sized(0.9F, 1.9F)
                        .build("drakaina"));
        public static final DeferredHolder<EntityType<?>, EntityType<DragonToothHook>> DRAGON_TOOTH_HOOK = ENTITY_TYPES.register("dragon_tooth_hook", () ->
                EntityType.Builder.<DragonToothHook>of(DragonToothHook::new, MobCategory.MISC)
                        .noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5)
                        .build("dragon_tooth_hook"));
        public static final DeferredHolder<EntityType<?>, EntityType<Dryad>> DRYAD = ENTITY_TYPES.register("dryad", () ->
                EntityType.Builder.of(Dryad::new, MobCategory.CREATURE)
                        .sized(0.48F, 1.8F)
                        .build("dryad"));
        public static final DeferredHolder<EntityType<?>, EntityType<Elpis>> ELPIS = ENTITY_TYPES.register("elpis", () ->
                EntityType.Builder.of(Elpis::new, MobCategory.CREATURE)
                        .sized(0.45F, 0.45F).fireImmune()
                        .build("elpis"));
        public static final DeferredHolder<EntityType<?>, EntityType<Empusa>> EMPUSA = ENTITY_TYPES.register("empusa", () ->
                EntityType.Builder.of(Empusa::new, MobCategory.MONSTER)
                        .sized(0.67F, 1.8F).fireImmune()
                        .build("empusa"));
        public static final DeferredHolder<EntityType<?>, EntityType<Fury>> FURY = ENTITY_TYPES.register("fury", () ->
                EntityType.Builder.of(Fury::new, MobCategory.MONSTER)
                        .sized(0.67F, 1.4F).fireImmune()
                        .build("fury"));
        public static final DeferredHolder<EntityType<?>, EntityType<Geryon>> GERYON = ENTITY_TYPES.register("geryon", () ->
                EntityType.Builder.of(Geryon::new, MobCategory.MONSTER)
                        .sized(1.98F, 4.96F).fireImmune()
                        .build("geryon"));
        public static final DeferredHolder<EntityType<?>, EntityType<Gigante>> GIGANTE = ENTITY_TYPES.register("gigante", () ->
                EntityType.Builder.of(Gigante::new, MobCategory.CREATURE)
                        .sized(1.98F, 4.79F)
                        .build("gigante"));
        public static final DeferredHolder<EntityType<?>, EntityType<GoldenRam>> GOLDEN_RAM = ENTITY_TYPES.register("golden_ram", () ->
                EntityType.Builder.of(GoldenRam::new, MobCategory.CREATURE)
                        .sized(0.96F, 1.56F)
                        .build("golden_ram"));
        public static final DeferredHolder<EntityType<?>, EntityType<Gorgon>> GORGON = ENTITY_TYPES.register("gorgon", () ->
                EntityType.Builder.of(Gorgon::new, MobCategory.MONSTER)
                        .sized(0.9F, 1.9F)
                        .build("gorgon"));
        public static final DeferredHolder<EntityType<?>, EntityType<Harpy>> HARPY = ENTITY_TYPES.register("harpy", () ->
                EntityType.Builder.of(Harpy::new, MobCategory.MONSTER)
                        .sized(0.7F, 1.8F)
                        .build("harpy"));
        public static final DeferredHolder<EntityType<?>, EntityType<Hydra>> HYDRA = ENTITY_TYPES.register("hydra", () ->
                EntityType.Builder.of(Hydra::new, MobCategory.MONSTER)
                        .sized(2.4F, 2.24F).fireImmune()
                        .build("hydra"));
        public static final DeferredHolder<EntityType<?>, EntityType<HydraHead>> HYDRA_HEAD = ENTITY_TYPES.register("hydra_head", () ->
                EntityType.Builder.of(HydraHead::new, MobCategory.MISC)
                        .sized(0.68F, 1.88F).noSummon()
                        .build("hydra_head"));
        public static final DeferredHolder<EntityType<?>, EntityType<GiantBoar>> GIANT_BOAR = ENTITY_TYPES.register("giant_boar", () ->
                EntityType.Builder.of(GiantBoar::new, MobCategory.MONSTER)
                        .sized(2.653F, 2.66F)
                        .build("giant_boar"));
        public static final DeferredHolder<EntityType<?>, EntityType<Lampad>> LAMPAD = ENTITY_TYPES.register("lampad", () ->
                EntityType.Builder.of(Lampad::new, MobCategory.CREATURE)
                        .sized(0.48F, 1.8F).fireImmune()
                        .build("lampad"));
        public static final DeferredHolder<EntityType<?>, EntityType<MadCow>> MAD_COW = ENTITY_TYPES.register("mad_cow", () ->
                EntityType.Builder.of(MadCow::new, MobCategory.MONSTER)
                        .sized(0.9F, 1.4F)
                        .build("mad_cow"));
        public static final DeferredHolder<EntityType<?>, EntityType<Makhai>> MAKHAI = ENTITY_TYPES.register("makhai", () ->
                EntityType.Builder.of(Makhai::new, MobCategory.CREATURE)
                        .sized(0.70F, 1.8F)
                        .build("makhai"));
        public static final DeferredHolder<EntityType<?>, EntityType<Minotaur>> MINOTAUR = ENTITY_TYPES.register("minotaur", () ->
                EntityType.Builder.of(Minotaur::new, MobCategory.MONSTER)
                        .sized(0.7F, 1.94F)
                        .build("minotaur"));
        public static final DeferredHolder<EntityType<?>, EntityType<Naiad>> NAIAD = ENTITY_TYPES.register("naiad", () ->
                EntityType.Builder.of(Naiad::new, MobCategory.WATER_CREATURE)
                        .sized(0.48F, 1.8F)
                        .build("naiad"));
        public static final DeferredHolder<EntityType<?>, EntityType<NemeanLion>> NEMEAN_LION = ENTITY_TYPES.register("nemean_lion", () ->
                EntityType.Builder.of(NemeanLion::new, MobCategory.MONSTER)
                        .sized(1.92F, 2.28F).fireImmune()
                        .build("nemean_lion"));
        public static final DeferredHolder<EntityType<?>, EntityType<Orthus>> ORTHUS = ENTITY_TYPES.register("orthus", () ->
                EntityType.Builder.of(Orthus::new, MobCategory.MONSTER)
                        .sized(0.6F, 0.85F).fireImmune()
                        .build("orthus"));
        public static final DeferredHolder<EntityType<?>, EntityType<Pegasus>> PEGASUS = ENTITY_TYPES.register("pegasus", () ->
                EntityType.Builder.of(Pegasus::new, MobCategory.CREATURE)
                        .sized(1.39F, 1.98F)
                        .build("pegasus"));
        public static final DeferredHolder<EntityType<?>, EntityType<Python>> PYTHON = ENTITY_TYPES.register("python", () ->
                EntityType.Builder.of(Python::new, MobCategory.MONSTER)
                        .sized(1.4F, 1.9F).fireImmune()
                        .build("python"));
        public static final DeferredHolder<EntityType<?>, EntityType<Satyr>> SATYR = ENTITY_TYPES.register("satyr", () ->
                EntityType.Builder.of(Satyr::new, MobCategory.CREATURE)
                        .sized(0.67F, 1.8F)
                        .build("satyr"));
        public static final DeferredHolder<EntityType<?>, EntityType<Scylla>> SCYLLA = ENTITY_TYPES.register("scylla", () ->
                EntityType.Builder.of(Scylla::new, MobCategory.WATER_CREATURE)
                        .sized(1.92F, 4.4F).fireImmune()
                        .build("scylla"));
        public static final DeferredHolder<EntityType<?>, EntityType<Shade>> SHADE = ENTITY_TYPES.register("shade", () ->
                EntityType.Builder.of(Shade::new, MobCategory.MONSTER)
                        .sized(0.67F, 1.8F).fireImmune()
                        .build("shade"));
        public static final DeferredHolder<EntityType<?>, EntityType<Siren>> SIREN = ENTITY_TYPES.register("siren", () ->
                EntityType.Builder.of(Siren::new, MobCategory.WATER_CREATURE)
                        .sized(0.6F, 1.9F)
                        .build("siren"));
        public static final DeferredHolder<EntityType<?>, EntityType<Sparti>> SPARTI = ENTITY_TYPES.register("sparti", () ->
                EntityType.Builder.of(Sparti::new, MobCategory.CREATURE)
                        .sized(0.6F, 1.98F)
                        .build("sparti"));
        public static final DeferredHolder<EntityType<?>, EntityType<Stymphalian>> STYMPHALIAN = ENTITY_TYPES.register("stymphalian", () ->
                EntityType.Builder.of(Stymphalian::new, MobCategory.MONSTER)
                        .sized(0.7F, 0.7F)
                        .build("stymphalian"));
        public static final DeferredHolder<EntityType<?>, EntityType<Talos>> TALOS = ENTITY_TYPES.register("talos", () ->
                EntityType.Builder.of(Talos::new, MobCategory.MONSTER)
                        .sized(1.98F, 4.96F).fireImmune()
                        .build("talos"));
        public static final DeferredHolder<EntityType<?>, EntityType<Triton>> TRITON = ENTITY_TYPES.register("triton", () ->
                EntityType.Builder.of(Triton::new, MobCategory.WATER_CREATURE)
                        .sized(0.6F, 1.9F)
                        .build("triton"));
        public static final DeferredHolder<EntityType<?>, EntityType<Unicorn>> UNICORN = ENTITY_TYPES.register("unicorn", () ->
                EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE)
                        .sized(1.39F, 1.98F)
                        .build("unicorn"));
        public static final DeferredHolder<EntityType<?>, EntityType<Whirl>> WHIRL = ENTITY_TYPES.register("whirl", () ->
                EntityType.Builder.of(Whirl::new, MobCategory.WATER_CREATURE)
                        .sized(2.9F, 5.0F)
                        .build("whirl"));
        // other
        public static final DeferredHolder<EntityType<?>, EntityType<BronzeFeather>> BRONZE_FEATHER = ENTITY_TYPES.register("bronze_feather", () ->
                EntityType.Builder.<BronzeFeather>of(BronzeFeather::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("bronze_feather"));
        public static final DeferredHolder<EntityType<?>, EntityType<Curse>> CURSE = ENTITY_TYPES.register("curse", () ->
                EntityType.Builder.<Curse>of(Curse::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("curse"));
        public static final DeferredHolder<EntityType<?>, EntityType<CurseOfCirce>> CURSE_OF_CIRCE = ENTITY_TYPES.register("curse_of_circe", () ->
                EntityType.Builder.<CurseOfCirce>of(CurseOfCirce::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("curse_of_circe"));
        public static final DeferredHolder<EntityType<?>, EntityType<Discus>> DISCUS = ENTITY_TYPES.register("discus", () ->
                EntityType.Builder.<Discus>of(Discus::new, MobCategory.MISC)
                        .sized(0.45F, 0.45F).noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("discus"));
        public static final DeferredHolder<EntityType<?>, EntityType<DragonTooth>> DRAGON_TOOTH = ENTITY_TYPES.register("dragon_tooth", () ->
                EntityType.Builder.<DragonTooth>of(DragonTooth::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("dragon_tooth"));
        public static final DeferredHolder<EntityType<?>, EntityType<GreekFire>> GREEK_FIRE = ENTITY_TYPES.register("greek_fire", () ->
                EntityType.Builder.<GreekFire>of(GreekFire::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("greek_fire"));
        public static final DeferredHolder<EntityType<?>, EntityType<HealingSpell>> HEALING_SPELL = ENTITY_TYPES.register("healing_spell", () ->
                EntityType.Builder.<HealingSpell>of(HealingSpell::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("healing_spell"));
        public static final DeferredHolder<EntityType<?>, EntityType<Palladium>> PALLADIUM = ENTITY_TYPES.register("palladium", () ->
                EntityType.Builder.of(Palladium::new, MobCategory.MISC)
                        .sized(0.98F, 2.24F).fireImmune()
                        .build("palladium"));
        public static final DeferredHolder<EntityType<?>, EntityType<PoisonSpit>> POISON_SPIT = ENTITY_TYPES.register("poison_spit", () ->
                EntityType.Builder.<PoisonSpit>of(PoisonSpit::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("poison_spit"));
        public static final DeferredHolder<EntityType<?>, EntityType<Spear>> SPEAR = ENTITY_TYPES.register("spear", () ->
                EntityType.Builder.<Spear>of(Spear::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F).noSummon().clientTrackingRange(4).updateInterval(20)
                        .build("spear"));
        public static final DeferredHolder<EntityType<?>, EntityType<ThrowingAxe>> THROWING_AXE = ENTITY_TYPES.register("throwing_axe", () ->
                EntityType.Builder.<ThrowingAxe>of(ThrowingAxe::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F).noSummon().clientTrackingRange(4).updateInterval(20)
                        .build("throwing_axe"));
        public static final DeferredHolder<EntityType<?>, EntityType<WaterSpell>> WATER_SPELL = ENTITY_TYPES.register("water_spell", () ->
                EntityType.Builder.<WaterSpell>of(WaterSpell::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("water_spell"));
        public static final DeferredHolder<EntityType<?>, EntityType<WebBall>> WEB_BALL = ENTITY_TYPES.register("web_ball", () ->
                EntityType.Builder.<WebBall>of(WebBall::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).fireImmune().noSummon().clientTrackingRange(4).updateInterval(10)
                        .build("web_ball"));
    }

    public static final class BlockEntityReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MobHeadBlockEntity>> CERBERUS_HEAD = BLOCK_ENTITY_TYPES.register("cerberus_head", () ->
                BlockEntityType.Builder.of((pos, state) ->
                                        new MobHeadBlockEntity(BlockEntityReg.CERBERUS_HEAD.get(), pos, state),
                                BlockReg.CERBERUS_HEAD.get())
                        .build(null)
        );

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MobHeadBlockEntity>> GIGANTE_HEAD = BLOCK_ENTITY_TYPES.register("gigante_head", () ->
                BlockEntityType.Builder.of((pos, state) ->
                                        new MobHeadBlockEntity(BlockEntityReg.GIGANTE_HEAD.get(), pos, state),
                                BlockReg.GIGANTE_HEAD.get())
                        .build(null)
        );

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MobHeadBlockEntity>> ORTHUS_HEAD = BLOCK_ENTITY_TYPES.register("orthus_head", () ->
                BlockEntityType.Builder.of((pos, state) ->
                                        new MobHeadBlockEntity(BlockEntityReg.ORTHUS_HEAD.get(), pos, state),
                                BlockReg.ORTHUS_HEAD.get())
                        .build(null)
        );

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VaseBlockEntity>> VASE = BLOCK_ENTITY_TYPES.register("vase", () -> {
            // create set of vase blocks using registry objects
            Set<Block> vaseBlocks = new HashSet<>();
            // 1.21: Use BuiltInRegistries.BLOCK.get() instead of DeferredHolder.create
            vaseBlocks.add(BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(MODID, "terracotta_vase")));
            for (final DyeColor dyeColor : DyeColor.values()) {
                vaseBlocks.add(BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(MODID, dyeColor.getSerializedName() + "_terracotta_vase")));
            }
            // create block entity type
            return BlockEntityType.Builder.of(VaseBlockEntity::new, vaseBlocks.toArray(new Block[0]))
                    .build(null);
        });
    }


    public static final class PotionReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
            // Brewing recipes are registered via @EventBusSubscriber in GFBrewingRecipes class
        }

        public static final DeferredHolder<Potion, Potion> MIRRORING = POTIONS.register("mirroring",
                () -> new Potion(new MobEffectInstance(MobEffectReg.MIRRORING, 3600)));
        public static final DeferredHolder<Potion, Potion> LONG_MIRRORING = POTIONS.register("long_mirroring",
                () -> new Potion(new MobEffectInstance(MobEffectReg.MIRRORING, 9600)));

        public static final DeferredHolder<Potion, Potion> CURSE_OF_CIRCE = POTIONS.register("curse_of_circe",
                () -> new Potion(new MobEffectInstance(MobEffectReg.CURSE_OF_CIRCE, 3600)));
        public static final DeferredHolder<Potion, Potion> LONG_CURSE_OF_CIRCE = POTIONS.register("long_curse_of_circe",
                () -> new Potion(new MobEffectInstance(MobEffectReg.CURSE_OF_CIRCE, 9600)));

        public static final DeferredHolder<Potion, Potion> SLOW_SWIM = POTIONS.register("slow_swim",
                () -> new Potion(new MobEffectInstance(MobEffectReg.SLOW_SWIM, 3600)));
        public static final DeferredHolder<Potion, Potion> LONG_SLOW_SWIM = POTIONS.register("long_slow_swim",
                () -> new Potion(new MobEffectInstance(MobEffectReg.SLOW_SWIM, 9600)));

    }

    public static final class MobEffectReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<MobEffect, MobEffect> CURSE_OF_CIRCE = MOB_EFFECTS.register("curse_of_circe", () -> new CurseOfCirceEffect());
        public static final DeferredHolder<MobEffect, MobEffect> MIRRORING = MOB_EFFECTS.register("mirroring", () -> new MirroringEffect());
        public static final DeferredHolder<MobEffect, MobEffect> PETRIFIED = MOB_EFFECTS.register("petrified", () -> new StunnedEffect());
        public static final DeferredHolder<MobEffect, MobEffect> PRISONER_OF_HADES = MOB_EFFECTS.register("prisoner_of_hades", () -> new PrisonerOfHadesEffect());
        public static final DeferredHolder<MobEffect, MobEffect> STUNNED = MOB_EFFECTS.register("stunned", () -> new StunnedEffect());
        public static final DeferredHolder<MobEffect, MobEffect> SLOW_SWIM = MOB_EFFECTS.register("slow_swim", () -> new SlowSwimEffect());

    }


    public static final class EnchantmentReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
            // NOTE: Enchantments are data-driven in 1.21 via JSON files in data/greekfantasy/enchantment/
        }
    }

    public static final class LootModifierReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ReplaceDropsLootModifier>> REPLACE_DROPS_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register(
                "replace_drops", ReplaceDropsLootModifier.CODEC_SUPPLIER);
        public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<BronzeScrapLootModifier>> BRONZE_SCRAP_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register(
                "bronze_scrap", BronzeScrapLootModifier.CODEC_SUPPLIER);
        public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<QuestLootModifier>> QUEST_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register(
                "quest", QuestLootModifier.CODEC_SUPPLIER);

    }

    public static final class RecipeReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<RecipeSerializer<?>, SalveRecipe.Serializer> OLIVE_SALVE = RECIPE_SERIALIZERS.register(SalveRecipe.Serializer.CATEGORY, () ->
                new SalveRecipe.Serializer());
    }

    public static final class ParticleReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORGON = PARTICLE_TYPES.register("gorgon", () ->
                new SimpleParticleType(true));
    }

    public static final class StructureProcessorReg {

        public static void register(IEventBus modEventBus) {
            modEventBus.addListener(StructureProcessorReg::registerStructureProcessors);
        }

        // 1.21: Structure processors are now registered via DeferredRegister
        @SuppressWarnings("unchecked")
        public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<LocStructureProcessor>> LOC_PROCESSOR_HOLDER =
                (DeferredHolder) STRUCTURE_PROCESSORS.register("loc", () -> (StructureProcessorType<LocStructureProcessor>) () -> LocStructureProcessor.CODEC);
        @SuppressWarnings("unchecked")
        public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SatyrStructureProcessor>> SATYR_PROCESSOR_HOLDER =
                (DeferredHolder) STRUCTURE_PROCESSORS.register("satyr", () -> (StructureProcessorType<SatyrStructureProcessor>) () -> SatyrStructureProcessor.CODEC);
        @SuppressWarnings("unchecked")
        public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CentaurStructureProcessor>> CENTAUR_PROCESSOR_HOLDER =
                (DeferredHolder) STRUCTURE_PROCESSORS.register("centaur", () -> (StructureProcessorType<CentaurStructureProcessor>) () -> CentaurStructureProcessor.CODEC);
        @SuppressWarnings("unchecked")
        public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<OceanVillageStructureProcessor>> OCEAN_VILLAGE_PROCESSOR_HOLDER =
                (DeferredHolder) STRUCTURE_PROCESSORS.register("ocean_village", () -> (StructureProcessorType<OceanVillageStructureProcessor>) () -> OceanVillageStructureProcessor.CODEC);

        private static void registerStructureProcessors(final FMLCommonSetupEvent event) {
            // 1.21: Structure processors are now registered via DeferredRegister, no additional work needed here
        }
    }

    public static final class StructureReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<StructureType<?>, StructureType<MazeStructure>> MAZE = STRUCTURE_TYPES.register("maze", () ->
                () -> MazeStructure.CODEC);
        public static final DeferredHolder<StructurePieceType, StructurePieceType> MAZE_ROOM = STRUCTURE_PIECE_TYPES.register("maze", () ->
                (config, tag) -> new MazePiece(tag));
        public static final DeferredHolder<StructureType<?>, StructureType<OceanVillageStructure>> OCEAN_VILLAGE = STRUCTURE_TYPES.register("ocean_village", () ->
                () -> OceanVillageStructure.CODEC);
    }

    public static final class FeatureReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static DeferredHolder<Feature<?>, OliveTreeFeature> OLIVE_TREE_FEATURE = FEATURES.register("olive_tree", () ->
                new OliveTreeFeature(TreeConfiguration.CODEC));
        public static DeferredHolder<Feature<?>, HarpyNestFeature> HARPY_NEST_FEATURE = FEATURES.register("harpy_nest", () ->
                new HarpyNestFeature(TreeConfiguration.CODEC));
    }

    public static final class PlacementTypeReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<DimensionFilter>> DIMENSION_FILTER = PLACEMENT_MODIFIER_TYPES.register("dimension", () -> () -> DimensionFilter.CODEC);
    }

    public static final class StructureModifierReg {

        public static void register(IEventBus modEventBus) {
            // DeferredRegister already registered in main register() method
        }

        public static final DeferredHolder<MapCodec<? extends StructureModifier>, MapCodec<AddSpawnsStructureModifier>> ADD_SPAWNS_MODIFIER = STRUCTURE_MODIFIERS.register("add_spawn", () -> AddSpawnsStructureModifier.CODEC);
    }

}
