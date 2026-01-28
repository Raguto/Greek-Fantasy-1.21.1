package greekfantasy;

import greekfantasy.util.ProperBrewingRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = GreekFantasy.MODID)
public class GFBrewingRecipes {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        
        // Mirroring potion (Awkward + Mirror = Mirroring)
        if (GreekFantasy.CONFIG.isMirroringEffectEnabled()) {
            builder.addRecipe(new ProperBrewingRecipe(
                Ingredient.of(createPotion(Potions.AWKWARD)),
                Ingredient.of(GFRegistry.ItemReg.MIRROR.get()),
                createPotion(GFRegistry.PotionReg.MIRRORING)
            ));
            // Long Mirroring (Mirroring + Redstone = Long Mirroring)
            builder.addRecipe(new ProperBrewingRecipe(
                Ingredient.of(createPotion(GFRegistry.PotionReg.MIRRORING)),
                Ingredient.of(Items.REDSTONE),
                createPotion(GFRegistry.PotionReg.LONG_MIRRORING)
            ));
        }
        
        // Curse of Circe potion (Awkward + Boar Ear = Curse of Circe)
        if (GreekFantasy.CONFIG.isCurseOfCirceEnabled()) {
            builder.addRecipe(new ProperBrewingRecipe(
                Ingredient.of(createPotion(Potions.AWKWARD)),
                Ingredient.of(GFRegistry.ItemReg.BOAR_EAR.get()),
                createPotion(GFRegistry.PotionReg.CURSE_OF_CIRCE)
            ));
            // Long Curse of Circe
            builder.addRecipe(new ProperBrewingRecipe(
                Ingredient.of(createPotion(GFRegistry.PotionReg.CURSE_OF_CIRCE)),
                Ingredient.of(Items.REDSTONE),
                createPotion(GFRegistry.PotionReg.LONG_CURSE_OF_CIRCE)
            ));
        }
        
        // Slow Swim potion (Awkward + Snakeskin = Slow Swim)
        builder.addRecipe(new ProperBrewingRecipe(
            Ingredient.of(createPotion(Potions.AWKWARD)),
            Ingredient.of(GFRegistry.ItemReg.SNAKESKIN.get()),
            createPotion(GFRegistry.PotionReg.SLOW_SWIM)
        ));
        // Long Slow Swim
        builder.addRecipe(new ProperBrewingRecipe(
            Ingredient.of(createPotion(GFRegistry.PotionReg.SLOW_SWIM)),
            Ingredient.of(Items.REDSTONE),
            createPotion(GFRegistry.PotionReg.LONG_SLOW_SWIM)
        ));
    }
    
    // Helper methods to create potion ItemStacks for brewing recipes
    private static ItemStack createPotion(DeferredHolder<Potion, Potion> potion) {
        return createPotion(potion.getDelegate());
    }

    private static ItemStack createPotion(Holder<Potion> potion) {
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }
}
