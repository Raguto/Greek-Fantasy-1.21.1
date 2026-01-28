package greekfantasy.item;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.entity.Sparti;
import greekfantasy.integration.RGCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForgeMod;

public class BidentItem extends SpearItem {

    public BidentItem(Tier tier, Item.Properties properties) {
        super(tier, 0.75F, properties, 4);
    }

    @Override
    protected void throwSpear(final Level level, final Player thrower, final ItemStack stack) {
        // Special behavior when enchanted
        if (greekfantasy.enchantment.EnchantmentUtils.getEnchantmentLevel(level, greekfantasy.enchantment.GFEnchantments.RAISING, stack) > 0
                && level instanceof ServerLevel
                && (!GreekFantasy.isRGLoaded() || RGCompat.getInstance().canUseRaising(thrower))) {
            // Attempt to spawn a Sparti where the player is looking
            final HitResult raytrace = ThunderboltItem.raytraceFromEntity(thrower, 5.0F);
            if (raytrace.getType() != HitResult.Type.MISS) {
                stack.hurtAndBreak(25, thrower, thrower.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                // spawn a sparti and set location
                final Sparti sparti = GFRegistry.EntityReg.SPARTI.get().create(level);
                sparti.setPos(raytrace.getLocation().x(), raytrace.getLocation().y(), raytrace.getLocation().z());
                sparti.yBodyRot = Mth.wrapDegrees(thrower.getYRot() + 180.0F);
                sparti.setLimitedLife(GreekFantasy.CONFIG.RAISING_SPARTI_LIFESPAN.get() * 20);
                thrower.playSound(SoundEvents.LAVA_EXTINGUISH, 0.8F, 0.9F + thrower.getRandom().nextFloat() * 0.2F);
                level.addFreshEntity(sparti);
                // entity data on spawn
                sparti.tame(thrower);
                sparti.setSpawning();
                sparti.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(new BlockPos((int) raytrace.getLocation().x, (int) raytrace.getLocation().y, (int) raytrace.getLocation().z)), MobSpawnType.MOB_SUMMONED, null);
            }
        } else {
            // Default behavior when not enchanted
            super.throwSpear(level, thrower, stack);
        }
    }
}
