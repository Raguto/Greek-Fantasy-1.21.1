package greekfantasy.network;

import greekfantasy.GreekFantasy;
import greekfantasy.GFRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sent from the server to the client to ensure that an entity
 * with the Curse of Circe potion effect is correctly rendered as a pig.
 **/
public record SCurseOfCircePacket(int entity, int duration) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SCurseOfCircePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "curse_of_circe"));

    public static final StreamCodec<ByteBuf, SCurseOfCircePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SCurseOfCircePacket::entity,
            ByteBufCodecs.INT, SCurseOfCircePacket::duration,
            SCurseOfCircePacket::new
    );

    public static SCurseOfCircePacket addEffect(final int entity, final int duration) {
        return new SCurseOfCircePacket(entity, duration);
    }

    public static SCurseOfCircePacket removeEffect(final int entity) {
        return new SCurseOfCircePacket(entity, -1);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the packet when it is received.
     *
     * @param message the SCurseOfCircePacket
     * @param context the IPayloadContext
     */
    public static void handlePacket(final SCurseOfCircePacket message, final IPayloadContext context) {
        context.enqueueWork(() -> {
            // locate the client-side entity and ensure it receives the mob effect information
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            Entity entity = mc.level.getEntity(message.entity);
            // add or remove effect based on duration
            if (entity instanceof LivingEntity livingEntity) {
                if(message.duration > 0) {
                    // add effect, play sound, and set persistence
                    livingEntity.addEffect(new MobEffectInstance(GFRegistry.MobEffectReg.CURSE_OF_CIRCE, message.duration));
                    livingEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F,
                            0.9F + livingEntity.getRandom().nextFloat() * 0.2F);
                    if (livingEntity instanceof Mob mob) {
                        mob.setPersistenceRequired();
                    }
                } else {
                    // remove effect and play sound
                    livingEntity.removeEffect(GFRegistry.MobEffectReg.CURSE_OF_CIRCE);
                    livingEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F,
                            0.9F + livingEntity.getRandom().nextFloat() * 0.2F);
                }
            }
        });
    }
}
