package greekfantasy.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import greekfantasy.GFRegistry;
import greekfantasy.GreekFantasy;
import greekfantasy.item.QuestItem;
import greekfantasy.util.Quest;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class QuestScreen extends Screen {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(GreekFantasy.MODID, "textures/gui/quest.png");

    private static final int IMAGE_WIDTH = 195;
    private static final int IMAGE_HEIGHT = 146;
    private static final int MARGIN_Y = 14;
    private static final int MARGIN_X = 12;

    private final int itemSlot;
    private final ItemStack itemStack;
    private final ResourceLocation questId;
    private final Quest quest;

    /** The x position of the upper-left corner **/
    private int x;
    /** The y position of the upper-left corner **/
    private int y;

    private MutableComponent title;
    private List<MutableComponent> components;

    protected QuestScreen(int itemSlot, ItemStack itemStack) {
        super(Component.empty());
        this.itemSlot = itemSlot;
        this.itemStack = itemStack;
        // 1.21: hasTag()/getTag() removed - use DataComponents instead
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if(itemStack.is(GFRegistry.ItemReg.QUEST.get()) && !customData.isEmpty() && customData.copyTag().contains(QuestItem.KEY_QUEST)) {
            this.questId = ResourceLocation.tryParse(customData.copyTag().getString(QuestItem.KEY_QUEST));
            this.quest = GreekFantasy.QUEST_MAP.getOrDefault(questId, Quest.EMPTY);
        } else {
            this.questId = ResourceLocation.withDefaultNamespace("empty");
            this.quest = Quest.EMPTY;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - IMAGE_WIDTH) / 2;
        this.y = (this.height - IMAGE_HEIGHT) / 2;
        this.title = this.quest.getDescription().withStyle(ChatFormatting.UNDERLINE);
        this.components = this.quest.getComponents();
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTick) {
        // draw background
        RenderSystem.setShaderTexture(0, BACKGROUND);
        poseStack.blit(BACKGROUND, this.x, this.y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        // draw title
        int maxWidth = IMAGE_WIDTH - MARGIN_X * 2;
        float startX = (this.width - this.font.width(title)) / 2.0F;
        float startY = this.y + MARGIN_Y;
        poseStack.drawString(this.font, title.getString(), startX, startY, 0, false);
        // draw components
        startX = this.x + MARGIN_X;
        startY += this.font.lineHeight + 4;
        for(MutableComponent text : components) {
            poseStack.drawWordWrap(this.font,FormattedText.of(text.getString()), (int) startX, (int) startY, maxWidth, 0);
            startY += this.font.wordWrapHeight(text.getString(), maxWidth) + 2;
        }
        // draw other widgets
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Override to prevent default blur/dark effect - render nothing so quest is visible
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
