package com.aquextheseal.woe.gui;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.elementdata.SetSkillLevelPacket;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

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
            blit(ms, this.leftPos + 20, this.topPos + 48, 0, 0, 32, 32, 32, 32);

            RenderSystem.setShaderTexture(0, magicPlayer.getMagicElement().getSecondSkill().getSkillIcon());
            blit(ms, this.leftPos + 121, this.topPos + 49, 0, 0, 32, 32, 32, 32);

            RenderSystem.setShaderTexture(0, magicPlayer.getMagicElement().getThirdSkill().getSkillIcon());
            blit(ms, this.leftPos + 220, this.topPos + 48, 0, 0, 32, 32, 32, 32);

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
        GuiComponent.drawCenteredString(poseStack, minecraft.font, "element menu", 128, 6, -12829636);
        this.font.draw(poseStack, "cost1", 23, 17, -12829636);
        this.font.draw(poseStack, "cost2", 124, 17, -12829636);
        this.font.draw(poseStack, "cost3", 221, 18, -12829636);

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
        this.addRenderableWidget(new ElementUpgradeButton(0, this.leftPos + 7, this.topPos + 25, 61, 20, 0, 19, UPGRADE_BUTTON_LOCATION,
                (e) -> {
        }));
        this.addRenderableWidget(new ElementUpgradeButton(1, this.leftPos + 106, this.topPos + 25, 61, 20,0, 19, UPGRADE_BUTTON_LOCATION,
                (e) -> {
        }));
        this.addRenderableWidget(new ElementUpgradeButton(2, this.leftPos + 205, this.topPos + 25, 61, 20,0, 19, UPGRADE_BUTTON_LOCATION,
                (e) -> {
        }));
    }

    public static class ElementUpgradeButton extends ImageButton {

        public int skillIndex;

        public ElementUpgradeButton(int skillIndex, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation pResourceLocation, OnPress pOnPress) {
            super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pResourceLocation, pOnPress);
            this.skillIndex = skillIndex;
        }

        @Override
        public void onPress() {
            super.onPress();
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            MagicPlayer magicPlayer = (MagicPlayer) player;

            assert magicPlayer != null;
            if (magicPlayer.getMagicElement() != null) {
                if (player.experienceLevel <= getSkillIndex(magicPlayer).getExpenseMultiplier()) {
                    player.closeContainer();
                    mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WITHER_BREAK_BLOCK, 1.0F));
                    player.displayClientMessage(new TranslatableComponent("You don't have enough Experience Points to level up this skill!").withStyle(ChatFormatting.RED), false);
                } else {
                    int val = getSkillIndex(magicPlayer).getLevel(skillIndex, magicPlayer);
                    MENetwork.CHANNEL.sendToServer(new SetSkillLevelPacket(skillIndex, val + 1));
                }
            }
        }

        public MagicSkill getSkillIndex(MagicPlayer magicPlayer) {
            return switch (skillIndex) {
                case 0 -> magicPlayer.getMagicElement().getFirstSkill();
                case 1 -> magicPlayer.getMagicElement().getSecondSkill();
                case 2 -> magicPlayer.getMagicElement().getThirdSkill();
                default -> null;
            };
        }
    }
}
