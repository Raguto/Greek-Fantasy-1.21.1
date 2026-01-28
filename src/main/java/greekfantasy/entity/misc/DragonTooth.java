package greekfantasy.entity.misc;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.entity.Sparti;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
// TODO 1.21: ITeleporter replaced with TeleportTransition
// TODO 1.21: NetworkHooks removed - getAddEntityPacket now uses default implementation

public class DragonTooth extends ThrowableItemProjectile {

    public DragonTooth(EntityType<? extends DragonTooth> entityType, Level level) {
        super(entityType, level);
    }

    private DragonTooth(Level level, LivingEntity thrower) {
        super(GFRegistry.EntityReg.DRAGON_TOOTH.get(), thrower, level);
    }

    private DragonTooth(Level level, double x, double y, double z) {
        super(GFRegistry.EntityReg.DRAGON_TOOTH.get(), x, y, z, level);
    }

    public static DragonTooth create(Level level, double x, double y, double z) {
        return new DragonTooth(level, x, y, z);
    }

    public static DragonTooth create(Level level, LivingEntity thrower) {
        return new DragonTooth(level, thrower);
    }

    @Override
    protected Item getDefaultItem() {
        return GFRegistry.ItemReg.DRAGON_TOOTH.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult raytrace) {
        super.onHitEntity(raytrace);
        final float damage = GreekFantasy.CONFIG.DRAGON_TOOTH_SPARTI_COUNT.get() > 0 ? 0.0F : 1.5F;
        raytrace.getEntity().hurt(damageSources().thrown(this, getOwner()), damage);
    }

    @Override
    protected void onHit(HitResult raytrace) {
        super.onHit(raytrace);
        if (!this.level().isClientSide() && this.isAlive()) {
            Entity thrower = getOwner();
            // spawn a configurable number of sparti
            for (int i = 0, n = GreekFantasy.CONFIG.DRAGON_TOOTH_SPARTI_COUNT.get(), life = 20 * GreekFantasy.CONFIG.DRAGON_TOOTH_SPARTI_LIFESPAN.get(); i < n; i++) {
                final Sparti sparti = GFRegistry.EntityReg.SPARTI.get().create(level());
                sparti.moveTo(raytrace.getLocation().x, raytrace.getLocation().y, raytrace.getLocation().z, 0, 0);
                level().addFreshEntity(sparti);
                if (thrower instanceof Player player) {
                    sparti.yBodyRot = Mth.wrapDegrees(thrower.getYRot() + 180.0F);
                    sparti.tame(player);
                }
                sparti.setSpawning();
                sparti.setLimitedLife(life);
                // 1.21: finalizeSpawn now takes 4 args instead of 5 (CompoundTag removed)
                sparti.finalizeSpawn((ServerLevelAccessor) level(), level().getCurrentDifficultyAt(new BlockPos((int) raytrace.getLocation().x, (int) raytrace.getLocation().y, (int) raytrace.getLocation().z)), MobSpawnType.MOB_SUMMONED, null);
            }
            discard();
        }
    }

    @Override
    public void tick() {
        Entity entity = getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            discard();
        } else {
            super.tick();
        }
    }

    // TODO 1.21: changeDimension API changed to use TeleportTransition
    /*@Override
    public Entity changeDimension(ServerLevel serverWorld, ITeleporter iTeleporter) {
        Entity entity = getOwner();
        if (entity != null && entity.level().dimension() != serverWorld.dimension()) {
            setOwner(null);
        }
        return super.changeDimension(serverWorld, iTeleporter);
    }*/

    // TODO 1.21: getAddEntityPacket removed - uses default implementation now

    @Override
    protected double getDefaultGravity() {
        return 0.11D;
    }
}
