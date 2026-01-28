package greekfantasy.network;

import com.mojang.serialization.Codec;
import greekfantasy.GreekFantasy;
import greekfantasy.util.Quest;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;

/**
 * Called when datapacks are (re)loaded.
 * Sent from the server to the client with a map of
 * ResourceLocation IDs and Quests
 **/
public record SQuestPacket(Map<ResourceLocation, Quest> data) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SQuestPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "quest_sync"));

    protected static final Codec<Map<ResourceLocation, Quest>> MAP_CODEC = Codec.unboundedMap(ResourceLocation.CODEC, Quest.CODEC);

    public static final StreamCodec<ByteBuf, SQuestPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(MAP_CODEC),
            SQuestPacket::data,
            SQuestPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the packet when it is received.
     *
     * @param message the SQuestPacket
     * @param context the IPayloadContext
     */
    public static void handlePacket(final SQuestPacket message, final IPayloadContext context) {
        context.enqueueWork(() -> {
            GreekFantasy.QUEST_MAP.clear();
            GreekFantasy.QUEST_MAP.putAll(message.data);
        });
    }
}
