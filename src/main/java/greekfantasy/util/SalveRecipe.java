package greekfantasy.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.item.OliveSalveItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import java.util.ArrayList;
import java.util.List;

public class SalveRecipe extends ShapelessRecipe {

    private WeightedMobEffectInstance useEffect;
    private int bonusEffectCount;
    private List<WeightedMobEffectInstance> bonusEffects;
    private int rolls;

    public SalveRecipe(String group, WeightedMobEffectInstance useEffect,
                       List<WeightedMobEffectInstance> bonusEffects, int rolls,
                       NonNullList<Ingredient> recipeItemsIn) {
        super(group, CraftingBookCategory.MISC, createSalve(useEffect, bonusEffects, rolls), recipeItemsIn);
        this.useEffect = useEffect;
        this.bonusEffects = bonusEffects;
        this.bonusEffectCount = this.bonusEffects.size();
        this.rolls = rolls;
    }

    private static ItemStack createSalve(final WeightedMobEffectInstance useEffect,
                                         final List<WeightedMobEffectInstance> bonusEffects, final int rolls) {
        ItemStack itemStack = new ItemStack(GFRegistry.ItemReg.OLIVE_SALVE.get());
        // write tag
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(OliveSalveItem.KEY_USE_EFFECT, useEffect.asTag());
        compoundTag.putInt(OliveSalveItem.KEY_ROLLS, rolls);
        ListTag list = new ListTag();
        for (WeightedMobEffectInstance instance : bonusEffects) {
            list.add(instance.asTag());
        }
        compoundTag.put(OliveSalveItem.KEY_BONUS_EFFECTS, list);
        // set tag
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
        return itemStack;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        final ItemStack result = super.assemble(input, registries);
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GFRegistry.RecipeReg.OLIVE_SALVE.get();
    }

    public WeightedMobEffectInstance getUseEffect() {
        return useEffect;
    }

    public List<WeightedMobEffectInstance> getBonusEffects() {
        return bonusEffects;
    }

    public int getRolls() {
        return rolls;
    }

    public int getBonusEffectCount() {
        return bonusEffectCount;
    }

    public static class Serializer implements RecipeSerializer<SalveRecipe> {

        public static final String CATEGORY = "olive_salve";
        private static final String KEY_USE_EFFECT = "use_effect";
        private static final String KEY_BONUS_EFFECTS = "bonus_effects";
        private static final String KEY_ROLLS = "rolls";

        private static final MapCodec<SalveRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.getGroup()),
                WeightedMobEffectInstance.CODEC.fieldOf(KEY_USE_EFFECT).forGetter(SalveRecipe::getUseEffect),
                WeightedMobEffectInstance.CODEC.listOf().fieldOf(KEY_BONUS_EFFECTS).forGetter(SalveRecipe::getBonusEffects),
                Codec.INT.fieldOf(KEY_ROLLS).forGetter(SalveRecipe::getRolls),
                Ingredient.CODEC_NONEMPTY
                    .listOf()
                    .fieldOf("ingredients")
                    .flatXmap(
                        list -> {
                            Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                            if (ingredients.length == 0) {
                                return DataResult.error(() -> "No ingredients for salve recipe");
                            // 1.21: ShapedRecipePattern.maxHeight/maxWidth are now private - use constants
                            // The max crafting grid is 3x3 = 9 slots
                            } else if (ingredients.length > 9) {
                                return DataResult.error(() -> "Too many ingredients for salve recipe");
                            }
                            return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                        },
                        DataResult::success
                    )
                    .forGetter(SalveRecipe::getIngredients)
            ).apply(instance, SalveRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, SalveRecipe> STREAM_CODEC = StreamCodec.of(
            Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public MapCodec<SalveRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SalveRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SalveRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            int ingredientCount = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            ingredients.replaceAll(ing -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

            int rolls = buffer.readInt();
            int bonusEffectCount = buffer.readInt();
            WeightedMobEffectInstance useEffect = WeightedMobEffectInstance.fromTag(buffer.readNbt());
            List<WeightedMobEffectInstance> bonusEffects = new ArrayList<>(bonusEffectCount);
            for (int i = 0; i < bonusEffectCount; i++) {
                bonusEffects.add(WeightedMobEffectInstance.fromTag(buffer.readNbt()));
            }
            return new SalveRecipe(group, useEffect, bonusEffects, rolls, ingredients);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SalveRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            buffer.writeInt(recipe.getRolls());
            buffer.writeInt(recipe.getBonusEffectCount());
            buffer.writeNbt(recipe.getUseEffect().asTag());
            for (int i = 0, n = recipe.getBonusEffectCount(); i < n; i++) {
                buffer.writeNbt(recipe.getBonusEffects().get(i).asTag());
            }
        }
    }
}
