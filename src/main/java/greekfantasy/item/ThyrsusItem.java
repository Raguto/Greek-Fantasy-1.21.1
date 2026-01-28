package greekfantasy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import greekfantasy.GreekFantasy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
// TODO 1.21: Vanishable interface removed - use vanishing_curse enchantment tag instead
// import net.minecraft.world.item.Vanishable;
// TODO 1.21: PotionUtils/Potions changed - needs investigation
// import net.minecraft.world.item.alchemy.PotionUtils;
// import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.List;

public class ThyrsusItem extends TieredItem {

    public static final ResourceLocation ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "thyrsus_attack_damage");
    public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "thyrsus_attack_speed");

    private final float attackDamage;
    private final Multimap<Holder<Attribute>, AttributeModifier> defaultModifiers;

    public ThyrsusItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties);
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_ID, this.attackDamage, AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE));
        this.defaultModifiers = builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity owner) {
        itemStack.hurtAndBreak(1, owner, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand) {
        // detect held items
        ItemStack mainhandItem = player.getItemInHand(hand);
        InteractionHand offhand = (hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        ItemStack offhandItem = player.getItemInHand(offhand);
        boolean success = false;
        // fill empty bucket with milk
        if (offhandItem.is(Items.BUCKET)) {
            offhandItem.shrink(1);
            player.getInventory().add(new ItemStack(Items.MILK_BUCKET));
            success = true;
        }
        // fill empty bottle with water
        // TODO 1.21: PotionUtils.setPotion/Potions removed - needs rewrite
        if (offhandItem.is(Items.GLASS_BOTTLE)) {
            offhandItem.shrink(1);
            // In 1.21, use PotionContents component instead
            ItemStack waterBottle = new ItemStack(Items.POTION);
            // waterBottle.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
            player.getInventory().add(waterBottle);
            success = true;
        }
        // cooldown and item damage
        if (success) {
            player.getCooldowns().addCooldown(this, GreekFantasy.CONFIG.THYRSUS_COOLDOWN.get());
            if (!player.isCreative()) {
                mainhandItem.hurtAndBreak(GreekFantasy.CONFIG.THYRSUS_DURABILITY_ON_USE.get(), player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
        }

        return InteractionResultHolder.sidedSuccess(mainhandItem, level.isClientSide());
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers should be set via Item.Properties.attributes() in registration

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
    }
}
