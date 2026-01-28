package greekfantasy.item;

import greekfantasy.GFRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class GFTiers {

    // TODO 1.21: TierSortingRegistry removed - tiers are now data-driven via tags
    // Incorrect blocks tag for now, should be replaced with proper tags
    public static final GFTier BIDENT = new GFTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 786, 8.0F, 3.0F, 14, () -> Ingredient.of(Items.BLAZE_ROD));
    public static final GFTier FLINT = new GFTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 88, 3.0F, 1.0F, 12, () -> Ingredient.of(Items.FLINT));
    public static final GFTier IVORY = new GFTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 835, 6.0F, 2.0F, 10, () -> Ingredient.of(Items.BONE));
    public static final GFTier THYRSUS = new GFTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 224, 3.0F, 1.5F, 10, () -> Ingredient.of(GFRegistry.ItemReg.PINECONE.get()));

    private static final class GFTier implements Tier {
        private final TagKey<Block> incorrectBlocksForDrops;
        private final int uses;
        private final float speed;
        private final float damage;
        private final int enchantmentValue;
        private Ingredient repairIngredient;
        private final Supplier<Ingredient> repairIngredientSupplier;

        public GFTier(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
            this.incorrectBlocksForDrops = incorrectBlocksForDrops;
            this.uses = uses;
            this.speed = speed;
            this.damage = damage;
            this.enchantmentValue = enchantmentValue;
            this.repairIngredientSupplier = repairIngredient;
        }

        @Override
        public int getUses() {
            return this.uses;
        }

        @Override
        public float getSpeed() {
            return this.speed;
        }

        @Override
        public float getAttackDamageBonus() {
            return this.damage;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return this.incorrectBlocksForDrops;
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantmentValue;
        }

        @Override
        public Ingredient getRepairIngredient() {
            if (null == this.repairIngredient) {
                this.repairIngredient = repairIngredientSupplier.get();
            }
            return this.repairIngredient;
        }
    }
}
