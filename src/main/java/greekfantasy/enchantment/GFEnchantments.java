package greekfantasy.enchantment;

import greekfantasy.GreekFantasy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Resource keys for Greek Fantasy enchantments.
 * In 1.21, enchantments are data-driven and defined in JSON files.
 */
public class GFEnchantments {
    public static final ResourceKey<Enchantment> BANE_OF_SERPENTS = key("bane_of_serpents");
    public static final ResourceKey<Enchantment> DAYBREAK = key("daybreak");
    public static final ResourceKey<Enchantment> FLYING = key("flying");
    public static final ResourceKey<Enchantment> FIREFLASH = key("fireflash");
    public static final ResourceKey<Enchantment> HUNTING = key("hunting");
    public static final ResourceKey<Enchantment> LORD_OF_THE_SEA = key("lord_of_the_sea");
    public static final ResourceKey<Enchantment> MIRRORING = key("mirroring");
    public static final ResourceKey<Enchantment> OVERSTEP = key("overstep");
    public static final ResourceKey<Enchantment> POISONING = key("poisoning");
    public static final ResourceKey<Enchantment> RAISING = key("raising");
    public static final ResourceKey<Enchantment> SILKSTEP = key("silkstep");
    public static final ResourceKey<Enchantment> SMASHING = key("smashing");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, name));
    }
}
