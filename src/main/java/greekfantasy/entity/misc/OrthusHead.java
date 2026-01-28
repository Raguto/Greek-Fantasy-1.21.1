package greekfantasy.entity.misc;

import greekfantasy.util.SummonBossUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OrthusHead extends ItemEntity {

    private boolean checkedCerberus;

    public OrthusHead(EntityType<? extends ItemEntity> entityType, Level world) {
        super(entityType, world);
    }

    public OrthusHead(Level level, double x, double y, double z, ItemStack itemStack) {
        this(level, x, y, z, itemStack, level.random.nextDouble() * 0.2D - 0.1D, 0.2D, level.random.nextDouble() * 0.2D - 0.1D);
    }

    public OrthusHead(Level level, double x, double y, double z, ItemStack itemStack, double dx, double dy, double dz) {
        this(EntityType.ITEM, level);
        this.setPos(x, y, z);
        this.setDeltaMovement(dx, dy, dz);
        this.setItem(itemStack);
        this.lifespan = (itemStack.isEmpty() ? 6000 : itemStack.getEntityLifespan(level));
    }

    public static OrthusHead create(Level level, double posX, double posY, double posZ, ItemStack itemstack) {
        return new OrthusHead(level, posX, posY, posZ, itemstack);
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!this.level().isClientSide() && this.isOnFire()) {
            SummonBossUtil.onOrthusHeadBurned(this.level(), this.blockPosition(), this.getOwner() != null ? this.getOwner().getUUID() : null);
        }
        super.remove(reason);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && !checkedCerberus && this.isOnFire() && this.isInLava()) {
            checkedCerberus = true;
            if (SummonBossUtil.onOrthusHeadBurned(this.level(), this.blockPosition(), this.getOwner() != null ? this.getOwner().getUUID() : null)) {
                this.discard();
            }
        }
    }

}
