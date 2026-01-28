package greekfantasy;

import greekfantasy.network.CPlayNotePacket;
import greekfantasy.network.SCurseOfCircePacket;
import greekfantasy.network.SQuestPacket;
import greekfantasy.network.SSongPacket;
import greekfantasy.util.CodecJsonDataManager;
import greekfantasy.util.Quest;
import greekfantasy.util.Song;
import greekfantasy.worldgen.maze.WeightedTemplateList;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Mod(GreekFantasy.MODID)
public class GreekFantasy {

    public static final String MODID = "greekfantasy";

    public static GFConfig CONFIG;

    public static final Logger LOGGER = LoggerFactory.getLogger(GreekFantasy.MODID);

    private static boolean isRpgGodsLoaded;

    private static final CodecJsonDataManager<Song> SONG_JSON_MANAGER = new CodecJsonDataManager<>("songs", Song.CODEC);
    public static final Map<ResourceLocation, Song> SONG_MAP = new HashMap<>();

    private static final CodecJsonDataManager<Quest> QUEST_JSON_MANAGER = new CodecJsonDataManager<>("quests", Quest.CODEC);
    public static final Map<ResourceLocation, Quest> QUEST_MAP = new HashMap<>();

    static {
        // Subscribe quest manager to sync data to clients
        QUEST_JSON_MANAGER.subscribeAsSyncable(SQuestPacket::new);
    }

    private static final CodecJsonDataManager<WeightedTemplateList> MAZE_PIECE_JSON_MANAGER = new CodecJsonDataManager<>("worldgen/maze_piece", WeightedTemplateList.CODEC);

    public GreekFantasy(IEventBus modEventBus, ModContainer modContainer) {
        // register config
        CONFIG = new GFConfig(modContainer);

        // registry listeners
        GFRegistry.register(modEventBus);

        // event listeners
        NeoForge.EVENT_BUS.register(GFEvents.ForgeHandler.class);
        // modEventBus.register(GFEvents.ModHandler.class); // 1.21: ModHandler class removed

        // client-only event listeners
        if (net.neoforged.fml.loading.FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.register(greekfantasy.client.GFClientEvents.ForgeHandler.class);
            modEventBus.register(greekfantasy.client.GFClientEvents.ModHandler.class);
        }

        // other listeners
        modEventBus.addListener(GreekFantasy::setup);
        modEventBus.addListener(GreekFantasy::loadConfig);
        modEventBus.addListener(GreekFantasy::reloadConfig);
        modEventBus.addListener(GreekFantasy::registerPayloads);
        NeoForge.EVENT_BUS.addListener(GreekFantasy::addReloadListeners);
    }

    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        // Register C2S (Client to Server) packets
        registrar.playToServer(
                CPlayNotePacket.TYPE,
                CPlayNotePacket.CODEC,
                CPlayNotePacket::handlePacket
        );
        // Register S2C (Server to Client) packets
        registrar.playToClient(
                SQuestPacket.TYPE,
                SQuestPacket.CODEC,
                SQuestPacket::handlePacket
        );
        registrar.playToClient(
                SCurseOfCircePacket.TYPE,
                SCurseOfCircePacket.CODEC,
                SCurseOfCircePacket::handlePacket
        );
    }

    public static void setup(final FMLCommonSetupEvent event) {
        isRpgGodsLoaded = ModList.get().isLoaded("rpggods");
    }

    public static void loadConfig(final ModConfigEvent.Loading event) {
        CONFIG.bake();
    }

    public static void reloadConfig(final ModConfigEvent.Reloading event) {
        CONFIG.bake();
    }

    public static void addReloadListeners(final AddReloadListenerEvent event) {
        event.addListener(QUEST_JSON_MANAGER);
        event.addListener(SONG_JSON_MANAGER);
        event.addListener(MAZE_PIECE_JSON_MANAGER);
    }

    public static WeightedTemplateList getMazePiece(final ResourceLocation id) {
        return MAZE_PIECE_JSON_MANAGER.getData().getOrDefault(id, WeightedTemplateList.EMPTY);
    }

    public static boolean isRGLoaded() {
        return isRpgGodsLoaded;
    }
}
