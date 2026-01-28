package greekfantasy.item;

import greekfantasy.GreekFantasy;
import greekfantasy.util.Quest;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class QuestItem extends Item {

    private static final String QUEST_NAME = "quest.name";
    public static final String KEY_QUEST = "QuestId";

    public QuestItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        // open GUI
        playerIn.startUsingItem(handIn);
        if (worldIn.isClientSide()) {
            greekfantasy.client.screen.ScreenLoader.openQuestScreen(playerIn, playerIn.getInventory().selected, itemstack);
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public String getDescriptionId(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if(!customData.isEmpty() && customData.copyTag().contains(KEY_QUEST)) {
            ResourceLocation questId = ResourceLocation.tryParse(customData.copyTag().getString(KEY_QUEST));
            if(questId != null) {
                return Quest.getDescriptionFromKey(questId);
            }
        }
        return super.getDescriptionId(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("item.greekfantasy.quest.tooltip").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
        return 67;
    }
}
