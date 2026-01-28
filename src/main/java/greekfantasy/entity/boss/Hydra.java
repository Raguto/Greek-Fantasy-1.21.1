package greekfantasy.entity.boss;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.entity.Cerastes;
import greekfantasy.entity.ai.MoveToStructureGoal;
import greekfantasy.entity.util.GFMobType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
// TODO 1.21: MobType removed
//import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import javax.annotation.Nullable;

public class Hydra extends Monster {

    private static final EntityDataAccessor<Byte> HEADS = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BYTE);
    private static final String KEY_HEADS = "Heads";

    public static final int MAX_HEADS = 11;

    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);

    public Hydra(final EntityType<? extends Hydra> type, final Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.66D)
                .add(Attributes.ARMOR, 14.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.STEP_HEIGHT, 0.6D);
    }

    public static Hydra spawnHydra(final ServerLevel level, final Cerastes cerastes) {
        Hydra entity = GFRegistry.EntityReg.HYDRA.get().create(level);
        entity.copyPosition(cerastes);
        entity.yBodyRot = cerastes.yBodyRot;
        if (cerastes.hasCustomName()) {
            entity.setCustomName(cerastes.getCustomName());
            entity.setCustomNameVisible(cerastes.isCustomNameVisible());
        }
        entity.setPersistenceRequired();
        entity.setPortalCooldown();
        level.addFreshEntity(entity);
        entity.finalizeSpawn(level, level.getCurrentDifficultyAt(cerastes.blockPosition()), MobSpawnType.CONVERSION, null);
        // remove the old cerastes
        cerastes.discard();
        // give potion effects
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60));
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60));
        // play sound
        entity.playSound(SoundEvents.WITHER_SPAWN, 1.2F, 1.0F);
        return entity;
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HEADS, (byte) 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new Hydra.MoveToTargetGoal(this, 1.0D, false));
        if(GreekFantasy.CONFIG.HYDRA_SEEK_LAIR.get()) {
            this.goalSelector.addGoal(4, new MoveToStructureGoal(this, 1.0D, 4, 8, 4, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "hydra_lair"), DefaultRandomPos::getPos));
        }
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D) {
            @Override
            public boolean canUse() {
                return Hydra.this.random.nextInt(40) == 0 && super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // boss info
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());

        if (!getPassengers().isEmpty() && !level().isClientSide()) {
            // determine if all heads are charred
            int charred = 0;
            HydraHead head;
            for (final Entity entity : getPassengers()) {
                head = (HydraHead) entity;
                if (head.isCharred()) {
                    charred++;
                }
            }
            // if all heads are charred, kill the hydra; otherwise, heal the hydra
            if (charred == getHeads()) {
                DamageSource source = this.getLastDamageSource();
                hurt(source != null ? source : damageSources().starve(), getMaxHealth() * 2.0F);
                getPassengers().forEach(e -> e.discard());
            } else if (getHealth() < getMaxHealth() && random.nextFloat() < 0.125F) {
                heal(1.25F * (getHeads() - charred));
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnDataIn) {
        SpawnGroupData result = super.finalizeSpawn(worldIn, difficulty, spawnType, spawnDataIn);
        addHead(0);
        addHead(1);
        addHead(2);
        setBaby(false);
        return result;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potioneffectIn) {
        if (potioneffectIn.getEffect().value() == MobEffects.POISON.value()) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, potioneffectIn, null);
            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);
            return event.getApplicationResult();
        }
        return super.canBeAffected(potioneffectIn);
    }

    // TODO 1.21: MobType removed
    //@Override
    //public MobType getMobType() {
        // TODO 1.21: MobType removed
        //return GFMobType.SERPENT;
    //}

    @Override
    public boolean requiresCustomPersistence() {
        return this.getHeads() != 3 || super.requiresCustomPersistence();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
        if (this.hasCustomName()) {
            bossInfo.setName(this.getCustomName());
        }
        bossInfo.setVisible(GreekFantasy.CONFIG.showHydraBossBar());
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    // Heads //

    public int getHeads() {
        return getEntityData().get(HEADS).intValue();
    }

    public void setHeads(final int heads) {
        getEntityData().set(HEADS, (byte) heads);
    }

    /**
     * Adds a head to this hydra
     *
     * @param id a unique id of the head
     * @return the hydra head entity
     */
    public HydraHead addHead(final int id) {
        // GreekFantasy.LOGGER.debug("Adding head with id " + id);
        if (!level().isClientSide()) {
            HydraHead head = GFRegistry.EntityReg.HYDRA_HEAD.get().create(level());
            head.moveTo(getX(), getY(), getZ(), 0, 0);
            // add the entity to the world (commented out bc of errors: "trying to add entity with duplicated UUID ...")
            // world.addEntity(head);
            // update the entity data
            head.setPartId(id);
            if (head.startRiding(this)) {
                // increase the number of heads
                setHeads(getHeads() + 1);
            } else {
                head.discard();
            }
            return head;
        }
        return null;
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        setHeads(Math.max(0, getHeads() - 1));
    }

    @Override
    public void ejectPassengers() {
        super.ejectPassengers();
        setHeads(0);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        for (final Entity e : getPassengers()) {
            if (e.getType() == GFRegistry.EntityReg.HYDRA_HEAD.get()) {
                e.remove(reason);
            }
        }
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < MAX_HEADS;
    }

    public void updatePassenger(Entity passenger, int id, Entity.MoveFunction callback) {
        if (this.hasPassenger(passenger)) {
            int totalHeads = Math.max(1, getHeads());
            int rowCount = totalHeads > (MAX_HEADS / 2) ? 2 : 1;
            int headsPerRow = (int) Math.ceil(totalHeads / (double) rowCount);
            int row = id / headsPerRow;
            int rowIndex = id % headsPerRow;
            int rowHeads = row == rowCount - 1 ? totalHeads - headsPerRow * (rowCount - 1) : headsPerRow;
            rowHeads = Math.max(rowHeads, 1);

            double baseRadius = 0.15D + 0.2D * getBbWidth();
            double radius = baseRadius + 0.35D * row;
            double centeredIndex = rowHeads > 1 ? (rowIndex - (rowHeads - 1) / 2.0D) : 0.0D;
            double spread = rowHeads > 1 ? 0.35D : 0.0D;
            double angle = Math.toRadians(this.getYRot()) + Math.PI / 2.0D + centeredIndex * spread;
            double dx = this.getX() + radius * Math.cos(angle);
            double dy = this.getY() + this.getBbHeight() * (0.4D + 0.08D * row);
            double dz = this.getZ() + radius * Math.sin(angle);
            callback.accept(passenger, dx, dy, dz);
        }
    }

    // Note: getPassengersRidingOffset removed in 1.21 - riding offset is now handled via EntityType builder

    // Sounds //

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.2F;
    }

    // NBT methods //

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(KEY_HEADS, (byte) getHeads());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setHeads(compound.getByte(KEY_HEADS));
    }

    class MoveToTargetGoal extends MeleeAttackGoal {

        public MoveToTargetGoal(PathfinderMob creature, double speedIn, boolean useLongMemoryIn) {
            super(creature, speedIn, useLongMemoryIn);
        }

        // Note: checkAndPerformAttack and getAttackReachSqr removed in 1.21
        // The hydra body doesn't actually attack - its heads do the attacking
        // Using tick() to set last hurt mob when close to target
        @Override
        public void tick() {
            super.tick();
            LivingEntity target = Hydra.this.getTarget();
            if (target != null && Hydra.this.distanceToSqr(target) <= 4.0D) {
                Hydra.this.setLastHurtMob(target);
            }
        }
    }
}
