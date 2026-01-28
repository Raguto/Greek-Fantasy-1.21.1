package greekfantasy.item;

import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class GFArmorMaterials {

    private static final ResourceLocation COPPER_INGOTS = ResourceLocation.fromNamespaceAndPath("forge", "ingots/copper");

    // Helper to create armor materials with a custom texture layer
    private static Holder<ArmorMaterial> createMaterial(String textureName, Map<ArmorItem.Type, Integer> defense,
            int enchantmentValue, Holder<SoundEvent> sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        return BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(
            new ArmorMaterial(
                defense,
                enchantmentValue,
                sound,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, textureName))),
                toughness,
                knockbackResistance
            )
        );
    }

    public static final Holder<ArmorMaterial> HELLENIC = createMaterial("hellenic",
            Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 5, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 2),
            18, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(TagKey.create(BuiltInRegistries.ITEM.key(), COPPER_INGOTS)));

    public static final Holder<ArmorMaterial> SNAKESKIN = createMaterial("snakeskin",
            Map.of(ArmorItem.Type.HELMET, 1, ArmorItem.Type.CHESTPLATE, 4, ArmorItem.Type.LEGGINGS, 5, ArmorItem.Type.BOOTS, 2),
            15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(GFRegistry.ItemReg.TOUGH_SNAKESKIN.get()));

    public static final Holder<ArmorMaterial> AVERNAL = createMaterial("avernal",
            Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 5, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 2),
            11, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> Ingredient.of(GFRegistry.ItemReg.AVERNAL_SHARD.get()));

    public static final Holder<ArmorMaterial> NEMEAN = createMaterial("nemean",
            Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 5, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 3),
            9, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.EMPTY);

    public static final Holder<ArmorMaterial> WINGED = createMaterial("winged",
            Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 5, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.BOOTS, 2),
            22, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, -0.05F, () -> Ingredient.of(GFRegistry.ItemReg.AVERNAL_FEATHER.get()));
}
