package greekfantasy.entity.misc;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.item.SpearItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
// TODO 1.21: NetworkHooks removed - getAddEntityPacket now uses default implementation
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;

public class Spear extends AbstractArrow {
    protected static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(Spear.class, EntityDataSerializers.ITEM_STACK);
    protected static final String KEY_ITEM = "Item";
    protected static final String KEY_DAMAGE = "DealtDamage";
    protected static final String KEY_SET_FIRE = "SetFire";
    protected boolean dealtDamage;
    protected boolean enchanted;
    protected int loyaltyLevel;
    protected int setFire;
    protected int returningTicks;

    protected ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/entity/spear/wooden_spear.png");

    public Spear(final EntityType<? extends Spear> type, final Level world) {
        super(type, world);
    }

    public Spear(Level world, LivingEntity thrower, ItemStack item, int setFire) {
        super(GFRegistry.EntityReg.SPEAR.get(), thrower, world, item, null);
        setArrowStack(item);
        this.setFire = setFire;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        ItemStack item = getPickupItem();
        return item.isEmpty() ? new ItemStack(GFRegistry.ItemReg.WOODEN_SPEAR.get()) : item;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ITEM, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (loyaltyLevel > 0 && !this.shouldReturnToThrower()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else if (loyaltyLevel > 0) {
                this.setNoPhysics(true);
                Vec3 vector3d = new Vec3(entity.getX() - this.getX(), entity.getEyeY() - this.getY(),
                        entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * loyaltyLevel, this.getZ());
                if (this.level().isClientSide()) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * loyaltyLevel;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        super.tick();
    }

    protected boolean shouldReturnToThrower() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayer) || !entity.isSpectator());
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.getEntityData().get(ITEM).copy();
    }

    protected void setArrowStack(final ItemStack stack) {
        this.getEntityData().set(ITEM, stack.copy());
        final ResourceLocation name = BuiltInRegistries.ITEM.getKey(stack.getItem());
        this.texture = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "textures/entity/spear/" + name.getPath() + ".png");
        // In 1.21, enchantments are data-driven - need registry lookup
        this.loyaltyLevel = stack.getEnchantmentLevel(this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOYALTY));
        this.enchanted = stack.hasFoil();
    }

    @Override
    public void onSyncedDataUpdated(final EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key.equals(ITEM)) {
            ItemStack stack = getPickupItem();
            final ResourceLocation name = BuiltInRegistries.ITEM.getKey(stack.getItem());
            this.texture = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "textures/entity/spear/" + name.getPath() + ".png");
            // In 1.21, enchantments are data-driven - need registry lookup
            this.loyaltyLevel = stack.getEnchantmentLevel(this.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOYALTY));
            this.enchanted = stack.hasFoil();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult raytrace) {
        Entity entity = raytrace.getEntity();
        dealtDamage = true;

        // Note: getMobType() and getDamageBonus(ItemStack, MobType) removed in 1.21
        // Enchantment damage bonuses are now applied differently through the damage system

        Entity thrower = getOwner();
        DamageSource source = damageSources().thrown(this, (thrower == null) ? this : thrower);
        SoundEvent sound = SoundEvents.TRIDENT_HIT;
        // attempt to set entity on fire
        if(setFire > 0) {
            entity.igniteForSeconds(setFire);
        }

        if (entity.hurt(source, (float) this.getBaseDamage())) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                // 1.21: EnchantmentHelper.doPostHurtEffects and doPostDamageEffects removed
                // These effects (thorns, fire aspect, etc.) are now handled automatically through the damage pipeline
                doPostHurtEffects(living);
            }
        }

        setDeltaMovement(getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        playSound(sound, 1.0F, 1.0F);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        ItemStack stack = getPickupItem();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!customData.isEmpty() && customData.copyTag().contains(SpearItem.KEY_MOB_EFFECT)) {
            final CompoundTag nbt = customData.copyTag().getCompound(SpearItem.KEY_MOB_EFFECT).copy();
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.getOptional(ResourceLocation.parse(nbt.getString(SpearItem.KEY_MOB_EFFECT))).orElse(null);
            if (effect != null) {
                nbt.putByte("Id", (byte) BuiltInRegistries.MOB_EFFECT.getId(effect));
            }
            MobEffectInstance effectInstance = MobEffectInstance.load(nbt);
            if(effectInstance != null) {
                living.addEffect(effectInstance);
            }
        }
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    @Override
    public void playerTouch(Player player) {
        Entity thrower = getOwner();
        if (thrower != null && thrower.getUUID() != player.getUUID()) {
            return;
        }
        super.playerTouch(player);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Item", 10)) {
            setArrowStack(ItemStack.parseOptional(this.registryAccess(), tag.getCompound(KEY_ITEM)));
        }
        dealtDamage = tag.getBoolean(KEY_DAMAGE);
        setFire = tag.getInt(KEY_SET_FIRE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        CompoundTag itemTag = new CompoundTag();
        getPickupItem().save(this.registryAccess(), itemTag);
        tag.put("Item", itemTag);
        tag.putBoolean(KEY_DAMAGE, dealtDamage);
        tag.putInt(KEY_SET_FIRE, setFire);
    }

    // TODO 1.21: getAddEntityPacket removed - uses default implementation now

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public boolean shouldRender(double dX, double dY, double dZ) {
        return true;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public boolean hasFoil() {
        return enchanted;
    }
}
