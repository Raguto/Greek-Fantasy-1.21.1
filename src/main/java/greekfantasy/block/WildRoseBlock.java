package greekfantasy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

public class WildRoseBlock extends FlowerBlock {

    private final Holder<MobEffect> mobEffect;
    private final int duration;

    public WildRoseBlock(Holder<MobEffect> mobEffect, int duration, Properties properties) {
        super(mobEffect, duration, properties);
        this.mobEffect = mobEffect;
        this.duration = duration;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockstate, Level level, BlockPos blockpos, Random rand) {
        VoxelShape shape = getShape(blockstate, level, blockpos, CollisionContext.empty());
        Vec3 center = shape.bounds().getCenter();
        double posX = blockpos.getX() + center.x;
        double posZ = blockpos.getZ() + center.z;
        if (rand.nextInt(3) == 0) {
            level.addParticle(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 1.0F, 0.6F, 0.92F), posX + rand.nextDouble() / 5.0D, blockpos.getY() + 0.5D - rand.nextDouble(), posZ + rand.nextDouble() / 5.0D, 0, 0, 0);
        }
    }

    @Override
    public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide() && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(this.mobEffect, this.duration * 8));
        }
    }

}
