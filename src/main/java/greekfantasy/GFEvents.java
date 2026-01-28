package greekfantasy;

// import greekfantasy.capability.FriendlyGuardian;
// import greekfantasy.capability.IFriendlyGuardian;
import greekfantasy.entity.Arion;
import greekfantasy.entity.Automaton;
import greekfantasy.entity.Cerastes;
import greekfantasy.entity.GoldenRam;
import greekfantasy.entity.Naiad;
import greekfantasy.entity.Orthus;
import greekfantasy.entity.Palladium;
import greekfantasy.entity.Triton;
import greekfantasy.entity.Whirl;
import greekfantasy.entity.ai.DolphinTemptByTritonGoal;
import greekfantasy.entity.ai.FollowWaterMobGoal;
import greekfantasy.entity.ai.MoveToStructureGoal;
import greekfantasy.entity.boss.Geryon;
import greekfantasy.entity.boss.GiantBoar;
import greekfantasy.entity.boss.NemeanLion;
import greekfantasy.entity.monster.Circe;
import greekfantasy.entity.monster.Shade;
import greekfantasy.integration.RGCompat;
import greekfantasy.item.HellenicArmorItem;
import greekfantasy.item.NemeanLionHideItem;
import greekfantasy.item.ThunderboltItem;
import greekfantasy.mob_effect.CurseOfCirceEffect;
import greekfantasy.network.SCurseOfCircePacket;
import greekfantasy.network.SQuestPacket;
import greekfantasy.network.SSongPacket;
import greekfantasy.util.SummonBossUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
// import net.neoforged.neoforge.resource.PathPackResources; // Removed in NeoForge 1.21

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class GFEvents {

    public static final class ForgeHandler {

    private static final List<PendingSummonCheck> PENDING_SUMMON_CHECKS = new ArrayList<>();
    private static long lastSummonProcessTick = -1L;


        /**
         * Used to spawn a shade with the player's XP when they die.
         *
         * @param event the death event
         **/
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onPlayerDeath(final LivingDeathEvent event) {
            if (!event.isCanceled() && !event.getEntity().level().isClientSide() && event.getEntity() instanceof Player player) {
                // attempt to spawn a shade
                if (!player.isSpectator() && player.experienceLevel > 3
                        && !player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)
                        && player.getRandom().nextFloat() * 100.0F < GreekFantasy.CONFIG.SHADE_SPAWN_CHANCE.get()) {
                    // save XP value
                    int xp = player.totalExperience;
                    // remove XP from player
                    player.giveExperienceLevels(-(player.experienceLevel + 1));
                    // give XP to shade and spawn into world
                    final Shade shade = GFRegistry.EntityReg.SHADE.get().create(player.level());
                    shade.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                    shade.setStoredXP(Mth.floor(xp * 0.9F));
                    shade.setOwnerUUID(player.getUUID());
                    shade.setPersistenceRequired();
                    player.level().addFreshEntity(shade);
                }
            }
        }

        /**
         * Used to summon a Geryon when a cow is killed and other spawn conditions are met
         *
         * @param event the living death event
         */
        @SubscribeEvent
        public static void onLivingDeath(final LivingDeathEvent event) {
            if (!event.isCanceled() && event.getEntity().isEffectiveAi() && event.getSource().getEntity() instanceof Player) {
                // check if the cow was killed by a player and if geryon can spawn here
                final BlockPos deathPos = event.getEntity().blockPosition();
                if (event.getEntity() instanceof Cow && Geryon.canGeryonSpawnOn(event.getEntity().level(), deathPos)) {
                    // check for Geryon Head blocks nearby
                    final List<BlockPos> heads = new ArrayList<>();
                    final int r = 3;
                    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                    for (int x = -r; x <= r; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -r; z <= r; z++) {
                                pos.setWithOffset(deathPos, x, y, z);
                                if (event.getEntity().level().getBlockState(pos).is(GFRegistry.BlockReg.GIGANTE_HEAD.get())) {
                                    heads.add(pos.immutable());
                                }
                                // if we found at least three heads, remove them and spawn a geryon
                                if (heads.size() >= 3) {
                                    heads.subList(0, 3).forEach(p -> event.getEntity().level().destroyBlock(p, false));
                                    final float yaw = Mth.wrapDegrees(event.getSource().getEntity().getYRot() + 180.0F);
                                    Geryon.spawnGeryon(event.getEntity().level(), deathPos, yaw);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Used to prevent or increase projectile damage when wearing certain armor
         * @param event the projectile impact event
         */
        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void onProjectileImpact(final ProjectileImpactEvent event) {
            if(!event.getProjectile().level().isClientSide()
                    && event.getRayTraceResult().getType() == HitResult.Type.ENTITY
                    && event.getRayTraceResult() instanceof EntityHitResult entityHitResult
                    && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                // determine dot product between entity and projectile
                final double dot = getDotProduct(livingEntity, event.getProjectile(), true);
                GreekFantasy.LOGGER.debug("dot=" + dot);
                final int achillesCount = HellenicArmorItem.getAchillesCount(livingEntity);
                // determine if the entity is wearing armor and immune to projectiles
                if(achillesCount > 0 && HellenicArmorItem.isImmune(livingEntity, event.getProjectile(), dot, achillesCount)) {
                    // reflect the projectile motion
                    event.getProjectile().setDeltaMovement(event.getProjectile().getDeltaMovement().multiply(-1.0D, 1.0D, -1.0D));
                    // cancel the event
                    event.setCanceled(true);
                    // damage the armor
                    HellenicArmorItem.damageArmor(livingEntity, 1);
                    return;
                }
                // determine if entity is wearing armor and weak to arrow projectiles
                if(achillesCount > 0 && event.getProjectile() instanceof AbstractArrow arrow
                        && HellenicArmorItem.isCritical(livingEntity, arrow, dot, achillesCount)) {
                    // double the damage of the projectile
                    arrow.setBaseDamage(arrow.getBaseDamage() * (2.0D + 0.5D * achillesCount));
                    event.setCanceled(false);
                    return;
                }
                // determine if the entity is wearing nemean lion hide and immune to projectile
                ItemStack helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
                if(achillesCount == 0 && helmet.is(GFRegistry.ItemReg.NEMEAN_LION_HIDE.get())
                        && NemeanLionHideItem.isImmune(livingEntity, event.getProjectile(), dot)) {
                    // reflect the projectile motion
                    event.getProjectile().setDeltaMovement(event.getProjectile().getDeltaMovement().multiply(-1.0D, 1.0D, -1.0D));
                    // cancel the event
                    event.setCanceled(true);
                    // damage the armor
                    helmet.hurtAndBreak(1, livingEntity, EquipmentSlot.HEAD);
                    return;
                }
            }
        }

        /**
         * @param first the first entity
         * @param second the second entity
         * @param horizontalOnly true if the dot product should only account for horizontal facing
         * @return the dot product between the facing directions of two entities
         */
        private static double getDotProduct(final Entity first, final Entity second, final boolean horizontalOnly) {
            Vec2 firstVec = new Vec2(horizontalOnly ? 0 : first.getXRot(), first.getYRot());
            Vec2 secondVec = new Vec2(horizontalOnly ? 0 : second.getXRot(), second.getYRot());
            Vec3 firstFacing = Vec3.directionFromRotation(firstVec);
            Vec3 secondFacing = Vec3.directionFromRotation(secondVec);
            GreekFantasy.LOGGER.debug("\nfirst=" + firstFacing + "\nsecond=" + secondFacing);
            return secondFacing.dot(firstFacing);
        }

        /**
         * Used to handle Prisoner of Hades effect
         * (updating portal cooldown and removing when out of the nether)
         * @param event
         */
        @SubscribeEvent
        public static void onLivingTick(final EntityTickEvent.Post event) {
            // Skip non-living entities
            if (!(event.getEntity() instanceof LivingEntity livingEntity)) {
                return;
            }
            // only handle event on server
            if(livingEntity.level().isClientSide()) {
                return;
            }

            // handle Prisoner of Hades mob effect
            if(livingEntity.hasEffect(GFRegistry.MobEffectReg.PRISONER_OF_HADES)) {
                // remove when not in nether
                if (livingEntity.level().dimension() != Level.NETHER
                        || (GreekFantasy.isRGLoaded() && livingEntity instanceof Player player
                            && RGCompat.getInstance().canRemovePrisonerEffect(player))) {
                    livingEntity.removeEffect(GFRegistry.MobEffectReg.PRISONER_OF_HADES);
                } else {
                    // set portal cooldown
                    livingEntity.setPortalCooldown();
                }
            }

            // TODO 1.21: silkstep enchantment requires data-driven enchantment system
            // stuckSpeedMultiplier field is now protected and cannot be accessed directly
            /* if (GreekFantasy.CONFIG.isSilkstepEnabled()
                    && livingEntity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(null) > 0
                    && (!(livingEntity instanceof Player player && player.getAbilities().flying))
                    && livingEntity.stuckSpeedMultiplier.lengthSqr() > 1.0E-7D) {
                boolean cobweb = false;
                AABB axisalignedbb = livingEntity.getBoundingBox();
                BlockPos blockpos = new BlockPos((int) (axisalignedbb.minX + 0.001D), (int) (axisalignedbb.minY + 0.001D), (int) (axisalignedbb.minZ + 0.001D));
                BlockPos blockpos1 = new BlockPos((int) (axisalignedbb.maxX - 0.001D), (int) (axisalignedbb.maxY - 0.001D), (int) (axisalignedbb.maxZ - 0.001D));
                BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
                entryloop:
                for (int i = blockpos.getX(); i <= blockpos1.getX(); ++i) {
                    for (int j = blockpos.getY(); j <= blockpos1.getY(); ++j) {
                        for (int k = blockpos.getZ(); k <= blockpos1.getZ(); ++k) {
                            blockpos$mutable.set(i, j, k);
                            if (livingEntity.level().getBlockState(blockpos$mutable).is(Blocks.COBWEB)) {
                                cobweb = true;
                                break entryloop;
                            }
                        }
                    }
                }
                if (cobweb) {
                   livingEntity.stuckSpeedMultiplier = Vec3.ZERO;
                }
            } */
        }

        /**
         * Used to change player pose when under Curse of Circe.
         * @param event
         */
        @SubscribeEvent
        public static void onPlayerTick(final PlayerTickEvent.Post event) {
            if(!event.getEntity().isAlive()) {
                return;
            }

            // update pose when player is riding nemean lion
            final boolean isRidingLion = event.getEntity().getVehicle() instanceof NemeanLion;
            final Pose currentPose = event.getEntity().getForcedPose();
            // update the forced pose
            if (isRidingLion && currentPose != Pose.FALL_FLYING) {
                // apply the forced pose
                event.getEntity().setForcedPose(Pose.FALL_FLYING);
                event.getEntity().setPose(Pose.FALL_FLYING);
            } else if (!isRidingLion && Pose.FALL_FLYING == currentPose) {
                // clear the forced pose
                event.getEntity().setForcedPose(null);
            }

            // update pose when player is under curse of circe (client-only to avoid blocking attacks)
            if (GreekFantasy.CONFIG.isCurseOfCirceEnabled()) {
                final boolean curseOfCirce = event.getEntity().hasEffect(GFRegistry.MobEffectReg.CURSE_OF_CIRCE);
                if (event.getEntity().level().isClientSide()) {
                    final Pose forcedPose = event.getEntity().getForcedPose();
                    if (curseOfCirce && forcedPose != Pose.SWIMMING) {
                        event.getEntity().setForcedPose(Pose.SWIMMING);
                        event.getEntity().setPose(Pose.SWIMMING);
                    } else if (!curseOfCirce && Pose.SWIMMING == forcedPose) {
                        event.getEntity().setForcedPose(null);
                    }
                } else if (!curseOfCirce && event.getEntity().getForcedPose() != null) {
                    event.getEntity().setForcedPose(null);
                }
            }

            // every few ticks, ensure that flying players can still fly
            if (GreekFantasy.CONFIG.isFlyingEnabled() && event.getEntity().level() instanceof ServerLevel &&
                    !event.getEntity().isCreative() && !event.getEntity().isSpectator() && event.getEntity().tickCount > 10
                    && event.getEntity().level().getGameTime() % 11 == 0) {
                // load saved data
                final GFSavedData data = GFSavedData.getOrCreate((ServerLevel) event.getEntity().level());
                // remove flying players who do not meet the conditions
                if (data.hasFlyingPlayer(event.getEntity()) && !GFSavedData.validatePlayer(event.getEntity())) {
                    data.removeFlyingPlayer(event.getEntity());
                }
            }
        }

        /**
         * Used to enable flying players when they equip enchanted winged sandals.
         *
         * @param event the equipment change event
         */
        @SubscribeEvent
        public static void onChangeEquipment(final LivingEquipmentChangeEvent event) {
            // Check which equipment was changed and if it is a player
            if (GreekFantasy.CONFIG.isFlyingEnabled() && event.getEntity() instanceof Player player && player.level() instanceof ServerLevel
                    && event.getSlot() == EquipmentSlot.FEET && event.getTo().is(GFRegistry.ItemReg.WINGED_SANDALS.get())) {
                GFSavedData data = GFSavedData.getOrCreate((ServerLevel) player.level());
                // ensure player meets conditions before enabling flight
                if (GFSavedData.validatePlayer(player)) {
                    data.addFlyingPlayer(player);
                }
            }
        }

        /**
         * Used to prevent players from using items while stunned.
         *
         * @param event the living attack event
         **/
        @SubscribeEvent
        public static void onPlayerAttack(final AttackEntityEvent event) {
            if (isStunnedOrPetrified(event.getEntity())) {
                // cancel the event
                event.setCanceled(true);
            }
        }

        /**
         * Used to prevent breaking of blocks when the player is stunned or petrified
         * @param event the break speed event
         */
        @SubscribeEvent
        public static void onBreakSpeed(final PlayerEvent.BreakSpeed event) {
            if (isStunnedOrPetrified(event.getEntity())) {
                // cancel the event
                event.setCanceled(true);
            }
        }

        /**
         * Used to prevent using items on blocks when the player is stunned or petrified
         * @param event the right click block event
         */
        @SubscribeEvent
        public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
            if (isStunnedOrPetrified(event.getEntity())) {
                // cancel the event
                event.setCanceled(true);
                return;
            }
            if (!event.isCanceled() && !event.getLevel().isClientSide() && event.getEntity() instanceof Player player) {
                BlockPos placedPos = event.getPos().relative(event.getFace());
                PENDING_SUMMON_CHECKS.add(new PendingSummonCheck(placedPos, player.getUUID(), 1));
            }
        }

        /**
         * Processes delayed summon checks after block placement.
         */
        @SubscribeEvent
        public static void onPlayerTickPost(final PlayerTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) {
                return;
            }
            if (player.level().isClientSide()) {
                return;
            }
            long gameTime = player.level().getGameTime();
            if (gameTime == lastSummonProcessTick) {
                return;
            }
            lastSummonProcessTick = gameTime;
            if (PENDING_SUMMON_CHECKS.isEmpty()) {
                return;
            }
            ServerLevel level = (ServerLevel) player.level();
            PENDING_SUMMON_CHECKS.removeIf(check -> {
                check.ticksLeft--;
                if (check.ticksLeft > 0) {
                    return false;
                }
                BlockPos pos = check.pos;
                if (!level.isLoaded(pos)) {
                    return true;
                }
                Player owner = level.getPlayerByUUID(check.playerId);
                if (owner != null) {
                }
                SummonBossUtil.onPlaceBronzeBlock(level, pos, level.getBlockState(pos), owner);
                return true;
            });
        }

        private static final class PendingSummonCheck {
            private final BlockPos pos;
            private final java.util.UUID playerId;
            private int ticksLeft;

            private PendingSummonCheck(final BlockPos pos, final java.util.UUID playerId, final int ticksLeft) {
                this.pos = pos;
                this.playerId = playerId;
                this.ticksLeft = ticksLeft;
            }
        }

        /**
         * @param entity the living entity
         * @return true if the entity is alive and has either the stunned or petrified effect
         */
        private static boolean isStunnedOrPetrified(@Nullable LivingEntity entity) {
            return entity != null && entity.isAlive() && (entity.hasEffect(GFRegistry.MobEffectReg.STUNNED) || entity.hasEffect(GFRegistry.MobEffectReg.PETRIFIED));
        }

        /**
         * Used to prevent mobs from attacking players when either the player
         * or the mob are under Curse of Circe, Stunned, or Petrified
         *
         * @param event the living target event
         **/
        @SubscribeEvent
        public static void onLivingTarget(final LivingChangeTargetEvent event) {
            if(null == event.getOriginalAboutToBeSetTarget() || event.getEntity().level().isClientSide()) {
                return;
            }
            // check mob or target for curse of circe
            if (GreekFantasy.CONFIG.isCurseOfCirceEnabled()
                    && event.getEntity() instanceof Mob mob
                    && event.getOriginalAboutToBeSetTarget() != mob.getLastHurtByMob()
                    && (mob.hasEffect(GFRegistry.MobEffectReg.CURSE_OF_CIRCE)
                        || event.getOriginalAboutToBeSetTarget().hasEffect(GFRegistry.MobEffectReg.CURSE_OF_CIRCE))) {
                // remove attack target - in NeoForge 1.21, use setCanceled instead of setNewTarget(null)
                event.setCanceled(true);
            }
            // check mob for stunned or petrified
            if (event.getEntity() instanceof Mob mob && isStunnedOrPetrified(mob)) {
                // remove attack target
                event.setCanceled(true);
            }
            // TODO: Convert to NeoForge attachment system - capabilities have been replaced with attachments
            // check mob for capability (DISABLED - LazyOptional removed in NeoForge 1.21)
            /*
            if(event.getEntity().getType() == EntityType.GUARDIAN
                    && event.getEntity() instanceof PathfinderMob mob
                    && event.getNewTarget() instanceof Player player) {
                LazyOptional<IFriendlyGuardian> CAP = event.getEntity().getCapability(GreekFantasy.FRIENDLY_GUARDIAN_CAP);
                if(CAP.isPresent() && CAP.orElse(FriendlyGuardian.EMPTY).isNeutralTowardPlayer(mob, player)) {
                    // remove attack target
                    event.setNewTarget(null);
                }
            }
            */
        }

        /**
         * Used to update client when Curse of Circe is applied
         * @param event the potion added event
         */
        @SubscribeEvent
        public static void onMobEffectStart(final MobEffectEvent.Added event) {
            if(!event.getEntity().level().isClientSide()
                    && GreekFantasy.CONFIG.isCurseOfCirceEnabled()
                    && event.getEffectInstance() != null
                    && event.getEffectInstance().is(GFRegistry.MobEffectReg.CURSE_OF_CIRCE)
                    && event.getOldEffectInstance() == null) {
                // update health
                if(event.getOldEffectInstance() == null) {
                    float health = Mth.clamp(event.getEntity().getHealth(), 1.0F, event.getEntity().getMaxHealth() + (float) CurseOfCirceEffect.HEALTH_MODIFIER);
                    event.getEntity().setHealth(health);
                }
                // Send packet to clients
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayersTrackingEntityAndSelf(event.getEntity(),
                        SCurseOfCircePacket.addEffect(event.getEntity().getId(), event.getEffectInstance().getDuration()));
            }
        }

        /**
         * Used to update client when Curse of Circe is removed
         * @param event the potion added event
         */
        @SubscribeEvent
        public static void onMobEffectRemove(final MobEffectEvent.Remove event) {
            if(!event.getEntity().level().isClientSide() && GreekFantasy.CONFIG.isCurseOfCirceEnabled()
                    && event.getEffectInstance() != null
                    && event.getEffectInstance().is(GFRegistry.MobEffectReg.CURSE_OF_CIRCE)) {
                // Send packet to clients
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayersTrackingEntityAndSelf(event.getEntity(),
                        SCurseOfCircePacket.removeEffect(event.getEntity().getId()));
            }
        }

        /**
         * Used to update client when Curse of Circe is expired
         * @param event the potion added event
         */
        @SubscribeEvent
        public static void onMobEffectExpire(final MobEffectEvent.Expired event) {
            if(!event.getEntity().level().isClientSide() && GreekFantasy.CONFIG.isCurseOfCirceEnabled()
                    && event.getEffectInstance() != null
                    && event.getEffectInstance().is(GFRegistry.MobEffectReg.CURSE_OF_CIRCE)) {
                // Send packet to clients
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayersTrackingEntityAndSelf(event.getEntity(),
                        SCurseOfCircePacket.removeEffect(event.getEntity().getId()));
            }
        }

        /**
         * Checks if a Bronze block was placed and notifies SpawnBossUtil
         **/
        @SubscribeEvent
        public static void onEntityPlaceBlock(final BlockEvent.EntityPlaceEvent event) {
            // ensure the block matches
            if (!event.isCanceled()
                    && event.getLevel() instanceof Level
                    && SummonBossUtil.isSummonBlock(event.getPlacedBlock())) {
                // delegate to SummonBossUtil
                SummonBossUtil.onPlaceBronzeBlock((Level) event.getLevel(), event.getPos(), event.getPlacedBlock(), event.getEntity());
            }
        }

        /**
         * Used to add a step height modifier to items with Overstep enchantment
         * @param event the attribute modifier event
         */
        @SubscribeEvent
        public static void onItemAttributeModifiers(final ItemAttributeModifierEvent event) {
            // TODO_ENCHANT: was checking GFRegistry.EnchantmentReg.OVERSTEP but disabled
        }

        /**
         * Used to add AI to Minecraft entities when they are spawned.
         * Also used to sometimes replace Witch with Circe when a witch is spawned.
         *
         * @param event the spawn event
         **/
        @SubscribeEvent
        public static void onEntityJoinWorld(final EntityJoinLevelEvent event) {
            // Replace witch with Circe based on config chance
            if (event.getEntity() != null && event.getEntity().getType() == EntityType.WITCH
                    && event.getLevel() instanceof ServerLevel level
                    && !event.getLevel().isClientSide()
                    && (event.getLevel().getRandom().nextDouble() * 100.0D) < GreekFantasy.CONFIG.CIRCE_SPAWN_CHANCE.get()) {
                // Remove the witch
                event.getEntity().discard();
                // Spawn Circe instead
                BlockPos pos = event.getEntity().blockPosition();
                final Circe circe = GFRegistry.EntityReg.CIRCE.get().create(level);
                if (circe != null) {
                    circe.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 
                                 event.getEntity().getYRot(), event.getEntity().getXRot());
                    level.addFreshEntity(circe);
                    circe.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null);
                }
            }
            
            if(event.getEntity() instanceof final PathfinderMob mob && !event.getEntity().level().isClientSide()) {
                // add wither skeleton goal to avoid orthus
                if(mob.getType() == EntityType.WITHER_SKELETON) {
                    mob.goalSelector.addGoal(3, new AvoidEntityGoal<>(mob, Orthus.class, 6.0F, 1.0D, 1.2D));
                }
                // add rabbit goal to avoid cerastes
                if(mob.getType() == EntityType.RABBIT && ((Rabbit)event.getEntity()).getVariant().id() != 99) {
                    mob.goalSelector.addGoal(3, new AvoidEntityGoal<>(mob, Cerastes.class, 6.0F, 1.0D, 1.2D,
                            e -> e instanceof Cerastes cerastes && !cerastes.isHiding()));
                }
                // add guardian goals to attack enemies and move to ocean villages
                // TODO: Convert to NeoForge attachment system - capabilities have been replaced with attachments
                // Guardian friendly capability is DISABLED - LazyOptional removed in NeoForge 1.21
                /*
                if(mob.getType() == EntityType.GUARDIAN) {
                    Predicate<LivingEntity> predicate = entity -> entity instanceof Enemy && !(entity instanceof Guardian) && entity.isInWater() && entity.distanceToSqr(mob) > 9.0D;
                    mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, LivingEntity.class, 10, true, false, predicate) {
                        @Override
                        public boolean canUse() {
                            return super.canUse() && this.mob.getCapability(GreekFantasy.FRIENDLY_GUARDIAN_CAP).orElse(FriendlyGuardian.EMPTY).isEnabled();
                        }
                    });
                    if(GreekFantasy.CONFIG.GUARDIAN_SEEK_OCEAN_VILLAGE.get()) {
                        mob.goalSelector.addGoal(3, new MoveToStructureGoal(mob, 1.0D, 4, 8, 10, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ocean_village"), BehaviorUtils::getRandomSwimmablePos) {
                            @Override
                            public boolean canUse() {
                                return super.canUse() && this.mob.getCapability(GreekFantasy.FRIENDLY_GUARDIAN_CAP).orElse(FriendlyGuardian.EMPTY).isEnabled();
                            }
                        });
                    }
                }
                */
                // add dolphin goals to be tempted by tritons and move to ocean villages
                if(mob.getType() == EntityType.DOLPHIN && mob instanceof Dolphin dolphin) {
                    mob.goalSelector.addGoal(2, new DolphinTemptByTritonGoal(dolphin, 0.9D, Ingredient.of(ItemTags.FISHES)));
                    if (GreekFantasy.CONFIG.DOLPHIN_SEEK_OCEAN_VILLAGE.get()) {
                        mob.goalSelector.addGoal(3, new MoveToStructureGoal(dolphin, 1.0D, 5, 10, 15, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "ocean_village"), BehaviorUtils::getRandomSwimmablePos));
                    }
                }
                // add drowned goals to attack tritons and naiads
                if(mob.getType() == EntityType.DROWNED) {
                    mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Triton.class, false));
                    mob.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(mob, Naiad.class, true, false));
                }
                // add zombie goal to attack automaton
                if(mob.getType() == EntityType.ZOMBIE || mob.getType() == EntityType.DROWNED || mob.getType() == EntityType.HUSK) {
                    mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Automaton.class, true));
                }
            }
        }

        /**
         * Used to add prevent monsters from spawning near Palladium blocks
         *
         * @param event the spawn event
         **/
        @SubscribeEvent
        public static void onLivingCheckSpawn(final MobSpawnEvent.SpawnPlacementCheck event) {
            final int cRadius = GreekFantasy.CONFIG.getPalladiumChunkRange();
            final int cVertical = GreekFantasy.CONFIG.getPalladiumYRange() / 2; // divide by 2 to center on block
            if (GreekFantasy.CONFIG.isPalladiumEnabled() && !event.getLevel().isClientSide()
                    && (event.getSpawnType() == MobSpawnType.NATURAL
                        || event.getSpawnType() == MobSpawnType.REINFORCEMENT
                        || event.getSpawnType() == MobSpawnType.PATROL
                        || event.getSpawnType() == MobSpawnType.SPAWNER)
                    && event.getLevel() instanceof ServerLevel level
                    && event.getEntityType().getBaseClass().isAssignableFrom(Enemy.class)){// && event.getEntity().canChangeDimensions()) {
                // determine spawn area
                final BlockPos eventPos = event.getPos();
                final ChunkPos eventChunkPos = new ChunkPos(eventPos);
                final ChunkPos minChunkPos = new ChunkPos(eventChunkPos.x - cRadius, eventChunkPos.z - cRadius);
                final ChunkPos maxChunkPos = new ChunkPos(eventChunkPos.x + cRadius, eventChunkPos.z + cRadius);
                final BlockPos minBlock = minChunkPos.getBlockAt(0, eventPos.getY() - cVertical, 0);
                final BlockPos maxBlock = maxChunkPos.getBlockAt(15, eventPos.getY() + cVertical, 15);
                // AABB constructor expects Vec3, not BlockPos - use AABB.of() or convert
                final AABB aabb = new AABB(minBlock.getX(), minBlock.getY(), minBlock.getZ(),
                                           maxBlock.getX() + 1, maxBlock.getY() + 1, maxBlock.getZ() + 1);

                // search each chunk in range for a palladium
                LevelEntityGetter<Entity> entityGetter = level.getEntities();
                entityGetter.get(EntityTypeTest.forClass(Palladium.class), aabb, e -> {
                    // In NeoForge 1.21, Event.Result was removed - use setCanceled for cancellable events
                    // or use specific result types for specific events
                    // For SpawnPlacementCheck, we use setResult with the event's result type
                    // Comment out for now - needs proper API check
                    // event.setCanceled(true);
                    return AbortableIterationConsumer.Continuation.ABORT;
                });
            }
        }

        /**
         * Used to replace ocelot with Nemean Lion when the former is struck by lightning
         * (while under the Strength potion effect)
         *
         * @param event the EntityStruckByLightning event
         */
        @SubscribeEvent
        public static void onEntityStruckByLightning(final EntityStruckByLightningEvent event) {
            if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getType() == EntityType.OCELOT
                    && livingEntity.hasEffect(MobEffects.DAMAGE_BOOST)
                    && livingEntity.level().getDifficulty() != Difficulty.PEACEFUL
                    && livingEntity.level().random.nextFloat() * 100.0F < GreekFantasy.CONFIG.NEMEAN_LION_LIGHTNING_CHANCE.get()) {
                // remove the entity and spawn a nemean lion
                NemeanLion lion = GFRegistry.EntityReg.NEMEAN_LION.get().create(event.getEntity().level());
                lion.copyPosition(event.getEntity());
                if (event.getEntity().hasCustomName()) {
                    lion.setCustomName(event.getEntity().getCustomName());
                    lion.setCustomNameVisible(event.getEntity().isCustomNameVisible());
                }
                lion.setPersistenceRequired();
                event.getEntity().level().addFreshEntity(lion);
                event.getEntity().discard();
            }
        }

        /**
         * Used to convert hoglins to giant boars.
         * Used to convert horses to arions.
         * Used to convert yellow sheep to golden rams.
         *
         * @param event the PlayerInteractEvent.EntityInteract event
         **/
        @SubscribeEvent
        public static void onPlayerInteract(final PlayerInteractEvent.EntityInteract event) {
            if(event.isCanceled() || !(event.getLevel() instanceof ServerLevel)) {
                return;
            }
            if(isStunnedOrPetrified(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
            if (handleBossTransformInteract((ServerLevel) event.getLevel(), event.getEntity(), event.getTarget(), event.getItemStack())) {
                event.setCanceled(true);
            }
        }

        /**
         * Used to convert hoglins to giant boars, horses to arions, and yellow sheep to golden rams
         * when the player clicks a specific entity part.
         *
         * @param event the PlayerInteractEvent.EntityInteractSpecific event
         */
        @SubscribeEvent
        public static void onPlayerInteractSpecific(final PlayerInteractEvent.EntityInteractSpecific event) {
            if (event.isCanceled() || !(event.getLevel() instanceof ServerLevel)) {
                return;
            }
            if (isStunnedOrPetrified(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
            if (handleBossTransformInteract((ServerLevel) event.getLevel(), event.getEntity(), event.getTarget(), event.getItemStack())) {
                event.setCanceled(true);
            }
        }

        private static boolean handleBossTransformInteract(final ServerLevel level, final Player player, final Entity target, final ItemStack itemStack) {
            if (target.isRemoved()) {
                return false;
            }
            // when player uses poisonous potato on adult hoglin while outside the nether
            if ((!GreekFantasy.CONFIG.GIANT_BOAR_NON_NETHER.get() || level.dimension() != Level.NETHER)
                    && target.getType() == EntityType.HOGLIN
                    && target instanceof Hoglin hoglin
                    && itemStack.is(GiantBoar.TRIGGER)) {
                if (!hoglin.isBaby()) {
                    // spawn giant boar and shrink the item stack
                    GiantBoar.spawnGiantBoar(level, hoglin);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return true;
                }
            }
            // when player uses enchanted golden apple on adult horse
            if (target.getType() == EntityType.HORSE
                    && target instanceof Horse horse
                    && itemStack.is(Arion.TRIGGER)) {
                if (!horse.isBaby() && horse.isTamed()) {
                    // spawn Arion and shrink the item stack
                    Arion.spawnArion(level, player, horse);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return true;
                }
            }
            // when player uses dragon breath on shearable yellow sheep
            if (target.getType() == EntityType.SHEEP
                    && target instanceof Sheep sheep
                    && itemStack.is(GoldenRam.TRIGGER)) {
                if (sheep.readyForShearing() && sheep.getColor() == DyeColor.YELLOW) {
                    // spawn Golden Ram and shrink the item stack
                    GoldenRam.spawnGoldenRam(level, player, sheep);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return true;
                }
            }
            return false;
        }


        /**
         * Canceled when the player is stunned or petrified.
         * Used to trigger Lord of the Sea when the player starts using an enchanted trident
         * @param event the use item start event
         */
        @SubscribeEvent
        public static void onPlayerStartUsingItem(final LivingEntityUseItemEvent.Start event) {
            if(isStunnedOrPetrified(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
            if(!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player
                    && !event.isCanceled() && event.getItem().is(Items.TRIDENT)
                    && greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(greekfantasy.enchantment.GFEnchantments.LORD_OF_THE_SEA, player, event.getItem()) > 0
                    && !player.getCooldowns().isOnCooldown(Items.TRIDENT)
                    && (!GreekFantasy.isRGLoaded() || RGCompat.getInstance().canUseLordOfTheSea(player))) {
                // cancel the event
                event.setCanceled(true);
                useLordOfTheSea(player, event.getItem());
            }
        }

        /**
         * Canceled when the player is stunned or petrified
         * @param event the use item tick event
         */
        @SubscribeEvent
        public static void onPlayerTickUsingItem(final LivingEntityUseItemEvent.Tick event) {
            if(isStunnedOrPetrified(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
        }

        /**
         * Canceled when the player is stunned or petrified.
         * Used to run daybreak enchantment when right clicking while holding a clock.
         * @param event the player right click item event
         */
        @SubscribeEvent
        public static void onPlayerRightClickItem(final PlayerInteractEvent.RightClickItem event) {
            if(isStunnedOrPetrified(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
            if(!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player
                    && !event.isCanceled() && event.getItemStack().is(Items.CLOCK)
                    && greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(greekfantasy.enchantment.GFEnchantments.DAYBREAK, player, event.getItemStack()) > 0
                    && player.level().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)
                    && player.level().getDayTime() % 24000L > 13000L
                    && (!GreekFantasy.isRGLoaded() || RGCompat.getInstance().canUseDaybreak(player))) {
                // cancel the event
                event.setCanceled(true);
                useDaybreak(player, event.getItemStack());
            }
        }

        // TODO: Convert to NeoForge attachment system
        // Capabilities have been replaced with attachments in NeoForge
        /* @SubscribeEvent
        public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof Guardian) {
                event.addCapability(FriendlyGuardian.REGISTRY_NAME, new FriendlyGuardian.Provider());
            }
        } */

        private static void useLordOfTheSea(final ServerPlayer player, final ItemStack item) {
            final BlockHitResult raytrace = ThunderboltItem.raytraceFromEntity(player, 48.0F);
            // add a lightning bolt at the resulting position
            if (raytrace.getType() != HitResult.Type.MISS) {
                final Whirl whirl = GFRegistry.EntityReg.WHIRL.get().create(player.level());
                final BlockPos pos = new BlockPos((int) raytrace.getLocation().x, (int) raytrace.getLocation().y, (int) raytrace.getLocation().z);
                // make sure there is enough water here
                if (player.level().getFluidState(pos).is(FluidTags.WATER)
                        && player.level().getFluidState(pos.below((int) Math.ceil(whirl.getBbHeight()))).is(FluidTags.WATER)) {
                    // summon a powerful whirl with limited life and mob attracting turned on
                    whirl.moveTo(raytrace.getLocation().x(), raytrace.getLocation().y() - whirl.getBbHeight(), raytrace.getLocation().z(), 0, 0);
                    player.level().addFreshEntity(whirl);
                    whirl.setLimitedLife(GreekFantasy.CONFIG.LORD_OF_THE_SEA_WHIRL_LIFESPAN.get() * 20);
                    whirl.setAttractMobs(true);
                    whirl.playSound(SoundEvents.TRIDENT_THUNDER.value(), 1.5F, 0.6F + whirl.getRandom().nextFloat() * 0.32F);
                    // summon a lightning bolt
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(player.level());
                    bolt.setSilent(true);
                    bolt.setPos(raytrace.getLocation().x(), raytrace.getLocation().y(), raytrace.getLocation().z());
                    bolt.setVisualOnly(true);
                    player.level().addFreshEntity(bolt);
                    // cooldown and item damage
                    player.getCooldowns().addCooldown(item.getItem(), 100);
                    if (!player.isCreative()) {
                        item.hurtAndBreak(25, player, EquipmentSlot.MAINHAND);
                    }
                }
            }
        }

        private static void useDaybreak(final ServerPlayer player, final ItemStack item) {
            final ServerLevel world = (ServerLevel) player.level();
            long nextDay = world.getLevelData().getDayTime() + 24000L;
            world.setDayTime(nextDay - nextDay % 24000L);
            // break the item
            // 1.21: broadcastBreakEvent removed - use onEquippedItemBroken
            player.onEquippedItemBroken(item.getItem(), EquipmentSlot.MAINHAND);
            if (!player.isCreative()) {
                item.shrink(1);
            }
        }
    }

    // TODO: Convert to NeoForge attachment system
    // Capabilities have been replaced with attachments in NeoForge
    /* @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IFriendlyGuardian.class);
    } */

//        @SubscribeEvent
//        public static void onAddPackFinders(final AddPackFindersEvent event) {
//            if(event.getPackType() == PackType.SERVER_DATA) {
//                // register RPG Gods data pack
//                if(GreekFantasy.isRGLoaded()) {
//                    GreekFantasy.LOGGER.info("Greek Fantasy detected RPG Gods, registering data pack now");
//                    registerAddon(event, "data_rpggods");
//                }
//            }
//        }

//        private static void registerAddon(final AddPackFindersEvent event, final String packName) {
//            event.addRepositorySource((packConsumer, constructor) -> {
//                Pack pack = Pack.create(GreekFantasy.MODID + ":" + packName, Component.literal("Greek Fantasy Gods"), true, (p) -> {
//                    Path path = ModList.get().getModFileById(GreekFantasy.MODID).getFile().findResource("/" + packName);
//                    return new PathPackResources(packName, true, path); //TODO CHECK
//                }, constructor, Pack.Position.TOP, PackSource.DEFAULT);
//
//                if (pack != null) {
//                    packConsumer.accept(pack);
//                } else {
//                    GreekFantasy.LOGGER.error(GreekFantasy.MODID + ": Failed to register data pack \"" + packName + "\"");
//                }
//            });
//        }
}
