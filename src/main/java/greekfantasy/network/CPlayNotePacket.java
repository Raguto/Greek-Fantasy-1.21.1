package greekfantasy.network;

import greekfantasy.GreekFantasy;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.core.registries.BuiltInRegistries;

/**
 * Sent from client to server when a player plays a note on an instrument.
 * Server then plays the sound and spawns particles for all nearby players.
 **/
public record CPlayNotePacket(int entity, int note, SoundEvent sound, float volume) implements CustomPacketPayload {

    public static final Type<CPlayNotePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "play_note")
    );

    public static final StreamCodec<FriendlyByteBuf, CPlayNotePacket> CODEC = StreamCodec.of(
            CPlayNotePacket::write,
            CPlayNotePacket::read
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void write(FriendlyByteBuf buf, CPlayNotePacket packet) {
        buf.writeInt(packet.entity);
        buf.writeInt(packet.note);
        buf.writeResourceLocation(BuiltInRegistries.SOUND_EVENT.getKey(packet.sound));
        buf.writeFloat(packet.volume);
    }

    public static CPlayNotePacket read(FriendlyByteBuf buf) {
        int entity = buf.readInt();
        int note = buf.readInt();
        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(buf.readResourceLocation());
        float volume = buf.readFloat();
        return new CPlayNotePacket(entity, note, sound, volume);
    }

    /**
     * Handles the packet when it is received on the server.
     */
    public static void handlePacket(final CPlayNotePacket message, final IPayloadContext context) {
        context.enqueueWork(() -> {
            // locate the server-side entity and play the sound at its location
            ServerPlayer player = (ServerPlayer) context.player();
            ServerLevel level = (ServerLevel) player.level();
            Entity entity = level.getEntity(message.entity);
            if (null == entity) {
                entity = player;
            }
            int note = Mth.clamp(message.note, 0, 25);
            final float volume = Mth.clamp(message.volume, 0.0F, 3.0F);
            final float pitch = (float) Math.pow(2.0D, (double) (note - 12) / 12.0D);
            double noteData = ((float) Math.pow(2.0D, (double) (note) / 24.0D));
            // send particles
            level.sendParticles(player, ParticleTypes.NOTE, true,
                    entity.getX(), entity.getEyeY(), entity.getZ(),
                    0, 1.0F, 0, 0, noteData);
            // play sound
            level.playSound(null, entity, message.sound, SoundSource.PLAYERS, volume, pitch);
        });
    }
}
