package greekfantasy.entity.misc;

import greekfantasy.GFRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DragonToothHook extends FishingHook {

    // TODO: In 1.21, FishingHook constructor with luck/speed bonuses is private
    // The luck and speed bonuses are no longer accessible from subclasses
    // These fields are kept for potential future use but currently not applied
    private final int luckBonus;
    private final int speedBonus;

    public DragonToothHook(EntityType<? extends FishingHook> entityType, Level level) {
        super(entityType, level);
        this.luckBonus = 0;
        this.speedBonus = 0;
    }

    public DragonToothHook(Player player, Level level, int luckBonus, int speedBonus) {
        super(GFRegistry.EntityReg.DRAGON_TOOTH_HOOK.get(), level);
        this.luckBonus = luckBonus;
        this.speedBonus = speedBonus;
        this.setOwner(player);
        float f = player.getXRot();
        float f1 = player.getYRot();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        double d0 = player.getX() - (double) f3 * 0.3D;
        double d1 = player.getEyeY();
        double d2 = player.getZ() - (double) f2 * 0.3D;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3((double) (-f3), (double) Mth.clamp(-(f5 / f4), -5.0F, 5.0F), (double) (-f2));
        double d3 = vec3.length();
        vec3 = vec3.multiply(0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D);
        this.setDeltaMovement(vec3);
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected void pullEntity(Entity hookedEntity) {
        super.pullEntity(hookedEntity);
        Entity entity = this.getOwner();
        LivingEntity owner = (entity instanceof LivingEntity) ? (LivingEntity) entity : null;
        if (entity != null && hookedEntity instanceof LivingEntity) {
            // determine distance to owner
            Vec3 vec3 = entity.position().subtract(this.position());
            // determine damage amount
            float damageAmount = 1.0F + (float) vec3.scale(0.25F).length();
            // hurt the hooked entity
            hookedEntity.hurt(hookedEntity.damageSources().mobProjectile(this, owner), damageAmount); //TODO bypass armor
        }
    }

    // TODO: In 1.21, shouldStopFishing is private in FishingHook and cannot be overridden
    // This custom check for the Dragon Tooth Rod is no longer called automatically
    // The base class will check for canPerformAction(FISHING_ROD_CAST) instead
    // To fully support the Dragon Tooth Rod, ensure it has the FISHING_ROD_CAST ItemAbility
    /*
    protected boolean shouldStopFishing(Player player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        boolean isMainhand = mainHandItem.is(GFRegistry.ItemReg.DRAGON_TOOTH_ROD.get());
        boolean isOffhand = offhandItem.is(GFRegistry.ItemReg.DRAGON_TOOTH_ROD.get());
        if (!player.isRemoved() && player.isAlive() && (isMainhand || isOffhand) && !(this.distanceToSqr(player) > getMaxDistanceSq())) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }
    */

    protected double getMaxDistanceSq() {
        return 1024.0D;
    }
}
