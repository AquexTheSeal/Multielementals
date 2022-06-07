package com.aquextheseal.woe.gui;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.elementdata.SetSkillLevelPacket;
import com.aquextheseal.woe.util.MEMechanicUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ElementMenuScreen extends AbstractContainerScreen<ElementMenuContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Multielementals.MODID, "textures/gui/element_menu.png");
    private static final ResourceLocation UPGRADE_BUTTON_LOCATION = new ResourceLocation(Multielementals.MODID, "textures/gui/element_upgrade.png");
    private final static HashMap<String, Object> guistate = ElementMenuContainer.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public ElementMenuScreen(ElementMenuContainer container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 286;
        this.imageHeight = 166;
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
        LocalPlayer player = Minecraft.getInstance().player;
        MagicPlayer magicPlayer = (MagicPlayer) player;

        assert magicPlayer != null;
        if (magicPlayer.getMagicElement() != null) {
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, GUI_TEXTURE);
            blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

            RenderSystem.setShaderTexture(0, magicPlayer.getMagicElement().getFirstSkill().getSkillIcon());
            blit(ms, this.leftPos + 25, this.topPos + 48, 0, 0, 32, 32, 32, 32);

            RenderSystem.setShaderTexture(0, magicPlayer.getMagicElement().getSecondSkill().getSkillIcon());
            blit(ms, this.leftPos + 125, this.topPos + 48, 0, 0, 32, 32, 32, 32);

            RenderSystem.setShaderTexture(0, magicPlayer.getMagicElement().getThirdSkill().getSkillIcon());
            blit(ms, this.leftPos + 221, this.topPos + 48, 0, 0, 32, 32, 32, 32);

            RenderSystem.disableBlend();
        }
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        LocalPlayer player = minecraft.player;
        MagicPlayer magicPlayer = (MagicPlayer) player;
        String skillCostKey = "info." + Multielementals.MODID + ".element_menu.skill_cost";
        float descScale = 0.75F;

        GuiComponent.drawCenteredString(poseStack, minecraft.font, new TranslatableComponent("info." + Multielementals.MODID + ".element_menu"), 141, 6, -12829636);

        // Skill 1
        int costMultiplier = MEMechanicUtil.getSkillIndex(0, magicPlayer).getExpenseMultiplier();
        int cost = ((MEMechanicUtil.getLevelOfSkill(0, magicPlayer) + 1) * 3) * costMultiplier;

        GuiComponent.drawCenteredString(poseStack, minecraft.font,
                new TranslatableComponent(skillCostKey, cost),
                36, 18, -12829636
        );

        poseStack.pushPose();
        poseStack.scale(descScale, descScale, descScale);
        drawWordWrap(poseStack,
                new TranslatableComponent(MEMechanicUtil.getSkillIndex(0, magicPlayer).getDescriptionKey()),
                7 + 8, 85 + 30, 98, -1
        );
        poseStack.popPose();

        // Skill 2
        int costMultiplier1 = MEMechanicUtil.getSkillIndex(1, magicPlayer).getExpenseMultiplier();
        int cost1 = ((MEMechanicUtil.getLevelOfSkill(1, magicPlayer) + 1) * 3) * costMultiplier1;

        GuiComponent.drawCenteredString(poseStack, minecraft.font,
                new TranslatableComponent(skillCostKey, cost1),
                137, 18, -12829636
        );

        poseStack.pushPose();
        poseStack.scale(descScale, descScale, descScale);
        drawWordWrap(poseStack,
                new TranslatableComponent(MEMechanicUtil.getSkillIndex(1, magicPlayer).getDescriptionKey()),
                105 + 33, 85 + 30, 98, -1
        );
        poseStack.popPose();

        int costMultiplier2 = MEMechanicUtil.getSkillIndex(2, magicPlayer).getExpenseMultiplier();
        int cost2 = ((MEMechanicUtil.getLevelOfSkill(2, magicPlayer) + 1) * 3) * costMultiplier2;

        GuiComponent.drawCenteredString(poseStack, minecraft.font,
                new TranslatableComponent(skillCostKey, cost2),
                234, 18, -12829636
        );

        poseStack.pushPose();
        poseStack.scale(descScale, descScale, descScale);
        drawWordWrap(poseStack,
                new TranslatableComponent(MEMechanicUtil.getSkillIndex(2, magicPlayer).getDescriptionKey()),
                194 + 55 + 18, 85 + 30, 98, -1
        );
        poseStack.popPose();
    }

    public void drawWordWrap(PoseStack stack, FormattedText pText, int pX, int pY, int pMaxWidth, int pColor) {
        for(FormattedCharSequence formattedcharsequence : minecraft.font.split(pText, pMaxWidth)) {
            GuiComponent.drawString(stack, minecraft.font, formattedcharsequence, pX, pY, pColor);
            pY += 9;
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

        this.addRenderableWidget(new ElementUpgradeButton(0, this.leftPos + 10, this.topPos + 29, 61, 18, 0, 0, 19, UPGRADE_BUTTON_LOCATION, 256, 256,
                (e) -> {
        }));
        this.addRenderableWidget(new ElementUpgradeButton(1, this.leftPos + 109, this.topPos + 29, 61, 18, 0, 0, 19, UPGRADE_BUTTON_LOCATION, 256, 256,
                (e) -> {
        }));
        this.addRenderableWidget(new ElementUpgradeButton(2, this.leftPos + 204, this.topPos + 29, 61, 18, 0, 0, 19, UPGRADE_BUTTON_LOCATION, 256, 256,
                (e) -> {
        }));
    }

    public static class ElementUpgradeButton extends ImageButton {

        public int skillIndex;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        MagicPlayer magicPlayer = (MagicPlayer) player;
        public int costMultiplier = MEMechanicUtil.getSkillIndex(skillIndex, magicPlayer).getExpenseMultiplier();
        public int cost = ((MEMechanicUtil.getLevelOfSkill(skillIndex, magicPlayer) + 1) * 3) * costMultiplier;

        public ElementUpgradeButton(int skillIndex, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, OnPress pOnPress) {
            super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress);
            this.skillIndex = skillIndex;
        }

        @Override
        public void onPress() {
            super.onPress();

            assert magicPlayer != null;
            if (magicPlayer.getMagicElement() != null) {

                if (player.experienceLevel < cost) {
                    player.closeContainer();
                    mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WITHER_BREAK_BLOCK, 1.0F));
                    player.displayClientMessage(new TranslatableComponent("info." + Multielementals.MODID + ".element_menu.invalid_xp").withStyle(ChatFormatting.RED), false);
                } else {
                    int val = MEMechanicUtil.getLevelOfSkill(skillIndex, magicPlayer);
                    int xpChange = -(cost * 25);

                    mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.PLAYER_LEVELUP, 1.75F));
                    mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.SHULKER_SHOOT, 1.75F));
                    player.giveExperiencePoints(xpChange);
                    MENetwork.CHANNEL.sendToServer(new SetSkillLevelPacket(skillIndex, val + 1, xpChange));
                }
            }
        }
    }
}
