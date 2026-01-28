package greekfantasy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import greekfantasy.GreekFantasy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.NeoForgeMod;
// TODO 1.21: import removed - event package changed
//import net.neoforged.event.entity.player.AttackEntityEvent;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class KnifeItem extends SwordItem {

    public static final ResourceLocation ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "knife_attack_damage");
    public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "knife_attack_speed");
    public static final ResourceLocation ATTACK_RANGE_ID = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "knife_attack_range");
    private Multimap<Holder<Attribute>, AttributeModifier> defaultModifiers;
    private final float attackSpeed;
    private final float attackRange;

    /**
     * @param iItemTier        the item tier, used for durability, etc.
     * @param baseAttackDamage the attack damage modifier (now set via properties)
     * @param attackSpeed      the attack speed modifier (now set via properties)
     * @param attackRange      the attack range modifier
     * @param properties       the item properties
     */
    public KnifeItem(Tier iItemTier, int baseAttackDamage, float attackSpeed, float attackRange, Properties properties) {
        // Note: In 1.21, SwordItem constructor changed - attack stats set via Item.Properties.attributes()
        super(iItemTier, properties);
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
    }

    // Note: getAttributeModifiers(EquipmentSlot, ItemStack) removed in 1.21
    // Attribute modifiers should be set via Item.Properties.attributes() in registration
    // The custom attack range modifier needs a different approach in 1.21
}
