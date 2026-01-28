package greekfantasy.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import greekfantasy.item.OliveSalveItem;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.registries.BuiltInRegistries;

public class WeightedMobEffectInstance extends WeightedEntry.IntrusiveBase {

    public static final WeightedMobEffectInstance EMPTY = new WeightedMobEffectInstance(MobEffects.REGENERATION, 0, 0, Weight.of(1));

    public static final String WEIGHT = "weight";

    public static final Codec<WeightedMobEffectInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("mob_effect").forGetter(WeightedMobEffectInstance::getMobEffectHolder),
            Codec.INT.optionalFieldOf("duration", 1).forGetter(WeightedMobEffectInstance::getDuration),
            Codec.INT.optionalFieldOf("amplifier", 1).forGetter(WeightedMobEffectInstance::getAmplifier),
            Weight.CODEC.optionalFieldOf("weight", Weight.of(1)).forGetter(WeightedMobEffectInstance::getWeight)
    ).apply(instance, WeightedMobEffectInstance::new));

    private final MobEffectInstance effectInstance;

    public WeightedMobEffectInstance(final Holder<MobEffect> effect, int duration, int amplifier, Weight weight) {
        this(new MobEffectInstance(effect, duration, amplifier), weight);
    }

    public WeightedMobEffectInstance(final MobEffectInstance effect, Weight weight) {
        super(weight);
        this.effectInstance = new MobEffectInstance(effect);
    }

    public static WeightedMobEffectInstance fromTag(final CompoundTag tag) {
        int weight = tag.getInt(WEIGHT);
        // In 1.21.1, MobEffectInstance.load expects "id" as a string resource location
        // If the tag has the old "Id" format, convert it
        if (tag.contains("Id") && !tag.contains("id")) {
            int oldId = tag.getByte("Id");
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.byId(oldId);
            if (effect != null) {
                String effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect).toString();
                tag.putString("id", effectId);
            }
        }
        MobEffectInstance effectInstance = MobEffectInstance.load(tag);
        if (effectInstance == null) {
            return EMPTY;
        }
        return new WeightedMobEffectInstance(effectInstance, Weight.of(weight));
    }

    public MobEffect getMobEffect() {
        return effectInstance.getEffect().value();
    }

    public Holder<MobEffect> getMobEffectHolder() {
        return effectInstance.getEffect();
    }

    public int getDuration() {
        return effectInstance.getDuration();
    }

    public int getAmplifier() {
        return effectInstance.getAmplifier();
    }

    public MobEffectInstance createMobEffectInstance() {
        return new MobEffectInstance(effectInstance);
    }

    public CompoundTag asTag() {
        CompoundTag effectTag = new CompoundTag();
        // In 1.21.1, use string resource location for "id" instead of numeric "Id"
        effectTag.putString("id", BuiltInRegistries.MOB_EFFECT.getKey(getMobEffect()).toString());
        effectTag.putByte("amplifier", (byte) getAmplifier());
        effectTag.putInt("duration", getDuration());
        effectTag.putInt(WEIGHT, getWeight().asInt());
        return effectTag;
    }
}
