package greekfantasy.entity.ai;

import greekfantasy.entity.util.HasCustomCooldown;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CooldownMeleeAttackGoal<T extends PathfinderMob & HasCustomCooldown> extends MeleeAttackGoal {
    protected final T entity;
    protected final int cooldown;

    public CooldownMeleeAttackGoal(T entity, double speedIn, boolean useLongMemory, final int cooldown) {
        super(entity, speedIn, useLongMemory);
        this.entity = entity;
        this.cooldown = cooldown;
    }

    // Note: checkAndPerformAttack removed in 1.21 - use tick() override instead
    @Override
    public void tick() {
        if (entity.hasNoCustomCooldown()) {
            super.tick();
        }
    }

    @Override
    protected void resetAttackCooldown() {
        super.resetAttackCooldown();
        entity.setCustomCooldown(cooldown);
    }
}
