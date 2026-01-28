package greekfantasy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class GFConfig {

    // items
    public final ModConfigSpec.IntValue APOLLO_BOW_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue ARTEMIS_BOW_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue AVERNAL_BOW_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue BAG_OF_WIND_DURATION;
    public final ModConfigSpec.IntValue BAG_OF_WIND_COOLDOWN;
    public final ModConfigSpec.IntValue BAG_OF_WIND_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue CONCH_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue DRAGON_TOOTH_SPARTI_COUNT;
    public final ModConfigSpec.IntValue DRAGON_TOOTH_SPARTI_LIFESPAN;
    public final ModConfigSpec.IntValue HORN_OF_PLENTY_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue STAFF_OF_HEALING_COOLDOWN;
    public final ModConfigSpec.IntValue STAFF_OF_HEALING_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue THUNDERBOLT_COOLDOWN;
    public final ModConfigSpec.IntValue THUNDERBOLT_DURABILITY_ON_USE;
    public final ModConfigSpec.IntValue THUNDERBOLT_DURABILITY_ON_FIREFLASH;
    public final ModConfigSpec.IntValue THYRSUS_COOLDOWN;
    public final ModConfigSpec.IntValue THYRSUS_DURABILITY_ON_USE;
    public final ModConfigSpec.BooleanValue UNICORN_HORN_CURES_EFFECTS;
    public final ModConfigSpec.IntValue UNICORN_HORN_DURABILITY_ON_USE;
    private final ModConfigSpec.DoubleValue WINGED_SANDALS_DURABILITY_CHANCE;
    private double wingedSandalsDurabilityChance;
    public final ModConfigSpec.IntValue WAND_OF_CIRCE_DURATION;
    public final ModConfigSpec.IntValue WAND_OF_CIRCE_COOLDOWN;
    public final ModConfigSpec.IntValue WAND_OF_CIRCE_DURABILITY_ON_USE;

    private final ModConfigSpec.BooleanValue HELM_HIDES_ARMOR;
    private boolean helmHidesArmor;

    // enchantments
    public final ModConfigSpec.BooleanValue BANE_OF_SERPENTS_ENABLED;
    public final ModConfigSpec.BooleanValue BANE_OF_SERPENTS_TRADEABLE;
    public final ModConfigSpec.BooleanValue DAYBREAK_ENABLED;
    public final ModConfigSpec.BooleanValue FIREFLASH_ENABLED;
    public final ModConfigSpec.BooleanValue FIREFLASH_DESTROYS_BLOCKS;
    private final ModConfigSpec.BooleanValue FLYING_ENABLED;
    private boolean isFlyingEnabled;
    public final ModConfigSpec.BooleanValue HUNTING_ENABLED;
    public final ModConfigSpec.BooleanValue HUNTING_TRADEABLE;
    public final ModConfigSpec.BooleanValue LORD_OF_THE_SEA_ENABLED;
    public final ModConfigSpec.IntValue LORD_OF_THE_SEA_WHIRL_LIFESPAN;
    private final ModConfigSpec.BooleanValue MIRRORING_ENCHANTMENT_ENABLED;
    public final ModConfigSpec.BooleanValue MIRRORING_TRADEABLE;
    private boolean isMirroringEnchantmentEnabled;
    public final ModConfigSpec.BooleanValue SMASHING_NERF;
    public final ModConfigSpec.BooleanValue SMASHING_TRADEABLE;
    private final ModConfigSpec.BooleanValue OVERSTEP_ENABLED;
    public final ModConfigSpec.BooleanValue OVERSTEP_TRADEABLE;
    private boolean isOverstepEnabled;
    private final ModConfigSpec.BooleanValue POISONING_ENABLED;
    public final ModConfigSpec.BooleanValue POISONING_TRADEABLE;
    private boolean isPoisoningEnabled;
    public final ModConfigSpec.BooleanValue RAISING_ENABLED;
    public final ModConfigSpec.IntValue RAISING_SPARTI_LIFESPAN;
    private final ModConfigSpec.BooleanValue SILKSTEP_ENABLED;
    public final ModConfigSpec.BooleanValue SILKSTEP_TRADEABLE;
    private boolean isSilkstepEnabled;

    // entity
    public final ModConfigSpec.DoubleValue CIRCE_SPAWN_CHANCE;
    public final ModConfigSpec.DoubleValue ELPIS_SPAWN_CHANCE;
    public final ModConfigSpec.BooleanValue GIANT_BOAR_NON_NETHER;
    public final ModConfigSpec.DoubleValue MEDUSA_LIGHTNING_CHANCE;
    public final ModConfigSpec.DoubleValue MEDUSA_SPAWN_CHANCE;
    public final ModConfigSpec.DoubleValue NEMEAN_LION_LIGHTNING_CHANCE;
    public final ModConfigSpec.DoubleValue SHADE_SPAWN_CHANCE;
    public final ModConfigSpec.DoubleValue SATYR_SHAMAN_CHANCE;
    public final ModConfigSpec.DoubleValue SCYLLA_SPAWN_CHANCE;
    private static final ResourceLocation SATYR_SONG_FALLBACK = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "greensleeves");
    private final ModConfigSpec.ConfigValue<? extends String> SATYR_SONG;
    private ResourceLocation satyrSong;
    public final ModConfigSpec.BooleanValue SHADE_IMMUNE_TO_NONOWNER;
    private final ModConfigSpec.BooleanValue SHOW_ARACHNE_BOSS_BAR;
    private boolean showArachneBossBar;
    private final ModConfigSpec.BooleanValue SHOW_CIRCE_BOSS_BAR;
    private boolean showCirceBossBar;
    private final ModConfigSpec.BooleanValue SHOW_CRETAN_BOSS_BAR;
    private boolean showCretanBossBar;
    private final ModConfigSpec.BooleanValue SHOW_GIANT_BOAR_BOSS_BAR;
    private boolean showGiantBoarBossBar;
    private final ModConfigSpec.BooleanValue SHOW_HYDRA_BOSS_BAR;
    private boolean showHydraBossBar;
    private final ModConfigSpec.BooleanValue SHOW_MEDUSA_BOSS_BAR;
    private boolean showMedusaBossBar;
    private final ModConfigSpec.BooleanValue SHOW_NEMEAN_LION_BOSS_BAR;
    private boolean showNemeanLionBossBar;
    private final ModConfigSpec.BooleanValue SHOW_PYTHON_BOSS_BAR;
    private boolean showPythonBossBar;
    private final ModConfigSpec.BooleanValue SHOW_SCYLLA_BOSS_BAR;
    private boolean showScyllaBossBar;

    // goals
    public final ModConfigSpec.BooleanValue ARA_SEEK_CAMP;
    public final ModConfigSpec.BooleanValue CENTAUR_SEEK_CAMP;
    public final ModConfigSpec.BooleanValue CYCLOPS_SEEK_CAVE;
    public final ModConfigSpec.BooleanValue DOLPHIN_SEEK_OCEAN_VILLAGE;
    public final ModConfigSpec.BooleanValue GIGANTE_SEEK_CAMP;
    public final ModConfigSpec.BooleanValue GUARDIAN_SEEK_OCEAN_VILLAGE;
    public final ModConfigSpec.BooleanValue HYDRA_SEEK_LAIR;
    public final ModConfigSpec.BooleanValue NEMEAN_LION_SEEK_DEN;
    public final ModConfigSpec.BooleanValue SATYR_SEEK_CAMP;
    public final ModConfigSpec.BooleanValue TRITON_SEEK_OCEAN_VILLAGE;

    // mob effect
    private final ModConfigSpec.BooleanValue CURSE_OF_CIRCE_ENABLED;
    private boolean isCurseOfCirceEnabled;
    public final ModConfigSpec.IntValue CURSE_OF_CIRCE_DURATION;
    private final ModConfigSpec.ConfigValue<List<? extends String>> CURSE_OF_CIRCE_WHITELIST;
    private List<? extends String> curseOfCirceWhitelist;
    private final ModConfigSpec.BooleanValue MIRRORING_EFFECT_ENABLED;
    private boolean isMirroringEffectEnabled;
    public final ModConfigSpec.BooleanValue PETRIFIED_NERF;
    public final ModConfigSpec.BooleanValue STUNNED_NERF;

    // palladium
    private final ModConfigSpec.BooleanValue PALLADIUM_ENABLED;
    private boolean palladiumEnabled;
    private final ModConfigSpec.IntValue PALLADIUM_CHUNK_RANGE;
    private int palladiumChunkRange;
    private final ModConfigSpec.IntValue PALLADIUM_Y_RANGE;
    private int palladiumYRange;

    public static final String WILDCARD = "*";

    // spawns
    private final ModConfigSpec.ConfigValue<List<? extends String>> SPAWN_DIMENSION_WHITELIST;
    private List<? extends String> spawnDimensionWhitelist;
    private final ModConfigSpec.BooleanValue IS_SPAWN_DIMENSION_WHITELIST;
    private boolean isSpawnDimensionWhitelist;

    // features
    private final ModConfigSpec.ConfigValue<List<? extends String>> FEATURE_DIMENSION_WHITELIST;
    private List<? extends String> featureDimensionWhitelist;
    private final ModConfigSpec.BooleanValue IS_FEATURE_DIMENSION_WHITELIST;
    private boolean isFeatureDimensionWhitelist;

    @SuppressWarnings("ConstantConditions")
    private static final String[] curseOfCirceWhitelistDefault = {
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PLAYER).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE_VILLAGER).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.HUSK).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VINDICATOR).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.WANDERING_TRADER).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ILLUSIONER).toString(),
            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PILLAGER).toString(),
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ara").toString(),
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "dryad").toString(),
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "lampad").toString(),
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "naiad").toString(),
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "satyr").toString()
    };

    public GFConfig(final ModContainer modContainer) {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("items");
        APOLLO_BOW_DURABILITY_ON_USE = builder.defineInRange("apollo_bow_durability_on_use", 1, 0, 64);
        ARTEMIS_BOW_DURABILITY_ON_USE = builder.defineInRange("artemis_bow_durability_on_use", 1, 0, 64);
        AVERNAL_BOW_DURABILITY_ON_USE = builder.defineInRange("avernal_bow_durability_on_use", 1, 0, 64);
        BAG_OF_WIND_DURATION = builder.defineInRange("bag_of_wind_duration", 400, 1, 24000);
        BAG_OF_WIND_COOLDOWN = builder.defineInRange("bag_of_wind_cooldown", 700, 0, 12000);
        BAG_OF_WIND_DURABILITY_ON_USE = builder.defineInRange("bag_of_wind_durability_on_use", 1, 0, 64);
        CONCH_DURABILITY_ON_USE = builder.defineInRange("conch_durability_on_use", 0, 0, 64);
        HELM_HIDES_ARMOR = builder.define("helm_hides_armor", true);
        DRAGON_TOOTH_SPARTI_COUNT = builder.defineInRange("dragon_tooth_sparti_count", 1, 0, 8);
        DRAGON_TOOTH_SPARTI_LIFESPAN = builder.defineInRange("dragon_tooth_sparti_lifespan", 300, 1, 24000);
        HORN_OF_PLENTY_DURABILITY_ON_USE = builder.defineInRange("horn_of_plenty_durability_on_use", 1, 0, 64);
        THUNDERBOLT_COOLDOWN = builder.defineInRange("thunderbolt_cooldown", 100, 0, 24000);
        THUNDERBOLT_DURABILITY_ON_USE = builder.defineInRange("thunderbolt_durability_on_use", 15, 0, 128);
        THUNDERBOLT_DURABILITY_ON_FIREFLASH = builder.defineInRange("thunderbolt_durability_on_fireflash", 25, 0, 128);
        THYRSUS_COOLDOWN = builder.defineInRange("thyrsus_cooldown", 60, 0, 24000);
        THYRSUS_DURABILITY_ON_USE = builder.defineInRange("thyrsus_durability_on_use", 1, 0, 64);
        STAFF_OF_HEALING_COOLDOWN = builder.defineInRange("staff_of_healing_cooldown", 35, 0, 24000);
        STAFF_OF_HEALING_DURABILITY_ON_USE = builder.defineInRange("staff_of_healing_durability_on_use", 1, 0, 64);
        UNICORN_HORN_CURES_EFFECTS = builder.define("unicorn_horn_cures_effects", true);
        UNICORN_HORN_DURABILITY_ON_USE = builder.defineInRange("unicorn_horn_durability_on_use", 1, 0, 64);
        WAND_OF_CIRCE_DURATION = builder.defineInRange("wand_of_circe_duration", 900, 1, 24000);
        WAND_OF_CIRCE_COOLDOWN = builder.defineInRange("wand_of_circe_cooldown", 50, 0, 24000);
        WAND_OF_CIRCE_DURABILITY_ON_USE = builder.defineInRange("wand_of_circe_durability_on_use", 1, 0, 64);
        WINGED_SANDALS_DURABILITY_CHANCE = builder
                .comment("Percent chance of winged sandals losing durability each tick")
                .defineInRange("winged_sandals_durability_chance", 0.0F, 0.0F, 1.0);
        builder.pop();

        builder.push("enchantments");
        BANE_OF_SERPENTS_ENABLED = builder.define("bane_of_serpents_enabled", true);
        BANE_OF_SERPENTS_TRADEABLE = builder.define("bane_of_serpents_tradeable_by_villagers", true);
        DAYBREAK_ENABLED = builder.define("daybreak_enabled", true);
        FIREFLASH_ENABLED = builder.define("fireflash_enabled", true);
        FIREFLASH_DESTROYS_BLOCKS = builder.define("fireflash_destroys_blocks", true);
        FLYING_ENABLED = builder.define("flying_enabled", true);
        HUNTING_ENABLED = builder.define("hunting_enabled", true);
        HUNTING_TRADEABLE = builder.define("hunting_tradeable_by_villagers", true);
        LORD_OF_THE_SEA_ENABLED = builder.define("lord_of_the_sea_enabled", true);
        LORD_OF_THE_SEA_WHIRL_LIFESPAN = builder.defineInRange("lord_of_the_sea_whirl_lifespan", 60, 1, 1200);
        MIRRORING_ENCHANTMENT_ENABLED = builder.define("mirroring_enabled", true);
        MIRRORING_TRADEABLE = builder.define("mirroring_tradeable_by_villagers", true);
        SMASHING_NERF = builder
                .comment("When true, Smashing applies slowness instead of stunning")
                .define("smashing_nerf", false);
        SMASHING_TRADEABLE = builder.define("smashing_tradeable_by_villagers", false);
        OVERSTEP_ENABLED = builder.define("overstep_enabled", true);
        OVERSTEP_TRADEABLE = builder.define("overstep_tradeable_by_villagers", true);
        POISONING_ENABLED = builder.define("poisoning_enabled", true);
        POISONING_TRADEABLE = builder.define("poisoning_tradeable_by_villagers", false);
        RAISING_ENABLED = builder.define("raising_enabled", true);
        RAISING_SPARTI_LIFESPAN = builder.defineInRange("raising_sparti_lifespan", 120, 1, 1200);
        SILKSTEP_ENABLED = builder.define("silkstep_enabled", true);
        SILKSTEP_TRADEABLE = builder.define("silkstep_tradeable_by_villagers", false);
        builder.pop();

        builder.push("mobs");
        CIRCE_SPAWN_CHANCE = builder.defineInRange("circe_spawn_chance", 2.0F, 0.0F, 100.0F);
        GIANT_BOAR_NON_NETHER = builder.comment("Whether a hoglin must be outside of the nether to be turned to a Giant Boar")
                .define("giant_boar_non_nether", true);
        ELPIS_SPAWN_CHANCE = builder
                .comment("Percent chance that opening a mysterious box spawns an Elpis")
                .defineInRange("elpis_spawn_chance", 60.0F, 0.0F, 100.0F);
        MEDUSA_LIGHTNING_CHANCE = builder
                .comment("Percent chance that lightning converts Gorgon to Medusa")
                .defineInRange("medusa_lightning_chance", 95.0F, 0.0F, 100.0F);
        MEDUSA_SPAWN_CHANCE = builder.defineInRange("medusa_spawn_chance", 0.8F, 0.0F, 100.0F);
        NEMEAN_LION_LIGHTNING_CHANCE = builder
                .comment("Percent chance that lightning converts strengthened Ocelot to Nemean Lion")
                .defineInRange("nemean_lion_lightning_chance", 100.0F, 0.0F, 100.0F);
        SHADE_SPAWN_CHANCE = builder.defineInRange("shade_spawn_chance", 100.0F, 0.0F, 100.0F);
        SATYR_SHAMAN_CHANCE = builder.defineInRange("satyr_shaman_chance", 24.0F, 0.0F, 100.0F);
        SCYLLA_SPAWN_CHANCE = builder
                .comment("Percent chance that creating a Charybdis also creates a Scylla")
                .defineInRange("scylla_spawn_chance", 55.0F, 0.0F, 100.0F);
        SATYR_SONG = builder.define("satyr_song", SATYR_SONG_FALLBACK.toString());
        SHADE_IMMUNE_TO_NONOWNER = builder.define("shade_immune_to_nonowner", true);
        SHOW_ARACHNE_BOSS_BAR = builder.define("show_arachne_boss_bar", false);
        SHOW_CIRCE_BOSS_BAR = builder.define("show_circe_boss_bar", false);
        SHOW_CRETAN_BOSS_BAR = builder.define("show_cretan_boss_bar", false);
        SHOW_GIANT_BOAR_BOSS_BAR = builder.define("show_giant_boar_boss_bar", true);
        SHOW_HYDRA_BOSS_BAR = builder.define("show_hydra_boss_bar", true);
        SHOW_MEDUSA_BOSS_BAR = builder.define("show_medusa_boss_bar", false);
        SHOW_NEMEAN_LION_BOSS_BAR = builder.define("show_nemean_lion_boss_bar", true);
        SHOW_PYTHON_BOSS_BAR = builder.define("show_python_boss_bar", true);
        SHOW_SCYLLA_BOSS_BAR = builder.define("show_scylla_boss_bar", true);
        builder.pop();

        builder.push("goals");
        ARA_SEEK_CAMP = builder.define("ara_seek_camp", true);
        CENTAUR_SEEK_CAMP = builder.define("centaur_seek_camp", false);
        CYCLOPS_SEEK_CAVE = builder.define("cyclops_seek_cave", false);
        DOLPHIN_SEEK_OCEAN_VILLAGE = builder.define("dolphin_seek_ocean_village", false);
        GIGANTE_SEEK_CAMP = builder.define("gigante_seek_camp", false);
        GUARDIAN_SEEK_OCEAN_VILLAGE = builder.define("guardian_seek_ocean_village", true);
        HYDRA_SEEK_LAIR = builder.define("hydra_seek_lair", true);
        NEMEAN_LION_SEEK_DEN = builder.define("nemean_lion_seek_den", true);
        SATYR_SEEK_CAMP = builder.define("satyr_seek_camp", false);
        TRITON_SEEK_OCEAN_VILLAGE = builder.define("triton_seek_ocean_village", true);
        builder.pop();

        builder.push("mob_effects");
        CURSE_OF_CIRCE_ENABLED = builder.define("curse_of_circe_enabled", true);
        CURSE_OF_CIRCE_DURATION = builder.defineInRange("curse_of_circe_duration", 900, 1, 24000);
        CURSE_OF_CIRCE_WHITELIST = builder.comment("Mobs that can be affected by the Curse of Circe.",
                        "Accepts entity id or mod id with wildcard.",
                        "Example: [\"minecraft:zombie\", \"othermod:" + WILDCARD + "\"]")
                .defineList("curse_of_circe_whitelist", List.of(curseOfCirceWhitelistDefault), o -> o instanceof String);
        MIRRORING_EFFECT_ENABLED = builder.define("mirroring_enabled", false);
        PETRIFIED_NERF = builder
                .comment("When true, Petrified applies slowness instead of stunning")
                .define("petrified_nerf", false);
        STUNNED_NERF = builder
                .comment("When true, Stunned applies slowness instead of stunning")
                .define("stunned_nerf", false);
        builder.pop();

        builder.push("palladium");
        PALLADIUM_ENABLED = builder.comment("Whether the Palladium can prevent monster spawns")
                .define("palladium_enabled", true);
        PALLADIUM_CHUNK_RANGE = builder.comment("The radius (in chunks) of the area protected by Palladium blocks (0=same chunk only)")
                .defineInRange("palladium_chunk_range", 2, 0, 3);
        PALLADIUM_Y_RANGE = builder.comment("The vertical area (in blocks) protected by Palladium blocks")
                .defineInRange("palladium_y_range", 128, 0, 255);
        builder.pop();

        builder.push("mob_spawns");
        SPAWN_DIMENSION_WHITELIST = builder.comment("Dimensions in which mobs can spawn.",
                        "Accepts dimension id or mod id with wildcard.",
                        "Example: [\"minecraft:the_nether\", \"rftoolsdim:" + WILDCARD + "\"]")
                .define("spawn_dimensions", Lists.newArrayList("minecraft:" + WILDCARD));
        IS_SPAWN_DIMENSION_WHITELIST = builder.comment("true if the above list is a whitelist, false for blacklist")
                .define("is_whitelist", true);
        builder.pop();

        builder.comment("Feature generation chances (higher number = more features)").push("features");
        FEATURE_DIMENSION_WHITELIST = builder.comment("Dimensions in which mobs can spawn.",
                        "Accepts dimension id or mod id with wildcard.",
                        "Example: [\"minecraft:the_nether\", \"rftoolsdim:" + WILDCARD + "\"]")
                .define("spawn_dimensions", Lists.newArrayList("minecraft:" + WILDCARD));
        IS_FEATURE_DIMENSION_WHITELIST = builder.comment("true if the above list is a whitelist, false for blacklist")
                .define("is_whitelist", true);
        builder.pop();

        // Build and register the config
        ModConfigSpec spec = builder.build();
        modContainer.registerConfig(ModConfig.Type.COMMON, spec);
    }

    /**
     * Finalizes some values that might otherwise be called
     * fairly often and cause potential lag. Not all config
     * values are baked, since many of them are only called
     * once-per-load or once-per-entity
     **/
    public void bake() {
        // items
        helmHidesArmor = HELM_HIDES_ARMOR.get();
        wingedSandalsDurabilityChance = WINGED_SANDALS_DURABILITY_CHANCE.get();
        // enchantments
        isFlyingEnabled = FLYING_ENABLED.get();
        isMirroringEnchantmentEnabled = MIRRORING_ENCHANTMENT_ENABLED.get();
        isOverstepEnabled = OVERSTEP_ENABLED.get();
        isPoisoningEnabled = POISONING_ENABLED.get();
        isSilkstepEnabled = SILKSTEP_ENABLED.get();
        // mob
        showArachneBossBar = SHOW_ARACHNE_BOSS_BAR.get();
        showCirceBossBar = SHOW_CIRCE_BOSS_BAR.get();
        showCretanBossBar = SHOW_CRETAN_BOSS_BAR.get();
        showGiantBoarBossBar = SHOW_GIANT_BOAR_BOSS_BAR.get();
        showHydraBossBar = SHOW_HYDRA_BOSS_BAR.get();
        showMedusaBossBar = SHOW_MEDUSA_BOSS_BAR.get();
        showNemeanLionBossBar = SHOW_NEMEAN_LION_BOSS_BAR.get();
        showPythonBossBar = SHOW_PYTHON_BOSS_BAR.get();
        showScyllaBossBar = SHOW_SCYLLA_BOSS_BAR.get();
        satyrSong = ResourceLocation.tryParse(SATYR_SONG.get());
        if(null == satyrSong) {
            satyrSong = SATYR_SONG_FALLBACK;
        }
        // mob effects
        isCurseOfCirceEnabled = CURSE_OF_CIRCE_ENABLED.get();
        curseOfCirceWhitelist = ImmutableList.copyOf(CURSE_OF_CIRCE_WHITELIST.get());
        isMirroringEffectEnabled = MIRRORING_EFFECT_ENABLED.get();
        // palladium
        palladiumEnabled = PALLADIUM_ENABLED.get();
        palladiumChunkRange = PALLADIUM_CHUNK_RANGE.get();
        palladiumYRange = PALLADIUM_Y_RANGE.get();
        // mob spawns
        spawnDimensionWhitelist = SPAWN_DIMENSION_WHITELIST.get();
        isSpawnDimensionWhitelist = IS_SPAWN_DIMENSION_WHITELIST.get();
        // feature spawns
        featureDimensionWhitelist = FEATURE_DIMENSION_WHITELIST.get();
        isFeatureDimensionWhitelist = IS_FEATURE_DIMENSION_WHITELIST.get();
    }

    // items

    public boolean helmHidesArmor() {
        return helmHidesArmor;
    }

    public double getWingedSandalsDurabilityChance() {
        return wingedSandalsDurabilityChance;
    }

    // enchantments

    public boolean isFlyingEnabled() {
        return isFlyingEnabled;
    }

    public boolean isPoisoningEnabled() {
        return isPoisoningEnabled;
    }

    public boolean isMirroringEnchantmentEnabled() {
        return isMirroringEnchantmentEnabled;
    }

    public boolean isOverstepEnabled() {
        return isOverstepEnabled;
    }

    public boolean isSilkstepEnabled() {
        return isSilkstepEnabled;
    }

    // mob

    public boolean showArachneBossBar() {
        return showArachneBossBar;
    }

    public boolean showCirceBossBar() {
        return showCirceBossBar;
    }

    public boolean showCretanBossBar() {
        return showCretanBossBar;
    }

    public boolean showGiantBoarBossBar() {
        return showGiantBoarBossBar;
    }

    public boolean showHydraBossBar() {
        return showHydraBossBar;
    }

    public boolean showMedusaBossBar() {
        return showMedusaBossBar;
    }

    public boolean showNemeanLionBossBar() {
        return showNemeanLionBossBar;
    }

    public boolean showPythonBossBar() {
        return showPythonBossBar;
    }

    public boolean showScyllaBossBar() {
        return showScyllaBossBar;
    }

    public ResourceLocation getSatyrSong() {
        return satyrSong;
    }

    // mob effects

    public boolean isCurseOfCirceEnabled() {
        return isCurseOfCirceEnabled;
    }

    public boolean isCurseOfCirceApplicable(@NonNull final LivingEntity entity) {
        ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return curseOfCirceWhitelist.contains(type.toString())
                || curseOfCirceWhitelist.contains(type.getNamespace() + ":" + WILDCARD);
    }

    public boolean isMirroringEffectEnabled() {
        return isMirroringEffectEnabled;
    }

    // palladium

    public boolean isPalladiumEnabled() {
        return palladiumEnabled;
    }

    public int getPalladiumChunkRange() {
        return palladiumChunkRange;
    }

    public int getPalladiumYRange() {
        return palladiumYRange;
    }

    // feature

    /**
     * @param level the level
     * @return true if features can be placed in the given dimension
     **/
    public boolean featureMatchesDimension(final Level level) {
        return matchesDimension(featureDimensionWhitelist, isFeatureDimensionWhitelist, level.dimension().location());
    }

    /**
     * @param level the level
     * @return true if mobs can spawn in the given dimension
     **/
    public boolean spawnMatchesDimension(final ServerLevel level) {
        return matchesDimension(spawnDimensionWhitelist, isSpawnDimensionWhitelist, level.dimension().location());
    }

    private static boolean matchesDimension(final List<? extends String> list, final boolean isWhitelist, final ResourceLocation dimensionId) {
        // check dimension id or mod id
        return isWhitelist == (list.contains(dimensionId.toString()) || list.contains(dimensionId.getNamespace() + ":" + WILDCARD));
    }
}
