package greekfantasy.item;

import com.google.common.collect.ImmutableMultimap;
import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
// TODO 1.21: Vanishable interface removed - use vanishing_curse enchantment tag instead
// import net.minecraft.world.item.Vanishable;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ClubItem extends TieredItem {

    public static final ResourceLocation ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "club_attack_damage");
    public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "club_attack_speed");
    public static final ResourceLocation ATTACK_KNOCKBACK_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "club_attack_knockback");
    public static final ResourceLocation MOVE_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "club_move_speed");
    public static final double ATTACK_KNOCKBACK_AMOUNT = 1.5D;
    protected final Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers;

    public ClubItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
        final double attackDamage = 5.5D + tier.getAttackDamageBonus();
        final double attackSpeed = -3.5D;
        final double moveSpeed = -0.1D;
        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_ID, ATTACK_KNOCKBACK_AMOUNT, AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(MOVE_SPEED_ID, moveSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        this.attributeModifiers = builder.build();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise the damage
     * on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // damage item
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(1, entityLiving, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers should be set via Item.Properties.attributes() in registration
    // The attributeModifiers field above is kept for reference but the override method is removed
}
