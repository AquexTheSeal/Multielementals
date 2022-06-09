package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, value = Dist.CLIENT)
public class OverlayEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();
            Player entity = Minecraft.getInstance().player;

            if (entity instanceof MagicPlayer magicPlayer) {
                MagicElement magic = magicPlayer.getMagicElement();

                if (magic != null) {
                    boolean isOnCooldown = magic.getFirstSkill().getCooldownCount(entity) > 0;
                    boolean isOnCooldown1 = magic.getSecondSkill().getCooldownCount(entity) > 0;
                    boolean isOnCooldown2 = magic.getThirdSkill().getCooldownCount(entity) > 0;
                    float rgb = isOnCooldown ? 0.2F : 1F;
                    float rgb2 = isOnCooldown1 ? 0.2F : 1F;
                    float rgb3 = isOnCooldown2 ? 0.2F : 1F;
                    String cooldownCount = "[ " + (magic.getFirstSkill().getCooldownCount(entity) / 20) + " ]";
                    String cooldownCount2 = "[ " + (magic.getSecondSkill().getCooldownCount(entity) / 20) + " ]";
                    String cooldownCount3 = "[ " + (magic.getThirdSkill().getCooldownCount(entity) / 20) + " ]";

                    int posX =  w / 2;
                    int posY = h / 2;

                    // First Skill
                    RenderSystem.depthMask(false);
                    RenderSystem.enableBlend();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                    RenderSystem.setShaderColor(rgb, rgb, rgb + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getFirstSkill().getSkillIcon(entity));
                    GuiComponent.blit(event.getMatrixStack(), w - 36, posY + -76, 0, 0, 32, 32, 32, 32);
                    GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, "" + magic.getFirstSkill().getLevel(magicPlayer), w - 20, posY + -50, -1);

                    if (isOnCooldown) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount, w - 20, posY + -64, -1);
                    }

                    // Second Skill
                    RenderSystem.setShaderColor(rgb2, rgb2, rgb2 + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getSecondSkill().getSkillIcon(entity));
                    GuiComponent.blit(event.getMatrixStack(), w - 36, posY + -36, 0, 0, 32, 32, 32, 32);
                    GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, "" + magic.getSecondSkill().getLevel(magicPlayer), w - 20, posY + -10, -1);

                    if (isOnCooldown1) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount2, w - 20, posY + -24, -1);
                    }

                    // Third Skill
                    RenderSystem.setShaderColor(rgb3, rgb3, rgb3 + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getThirdSkill().getSkillIcon(entity));
                    GuiComponent.blit(event.getMatrixStack(), w - 36, posY + 4, 0, 0, 32, 32, 32, 32);
                    GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, "" + magic.getThirdSkill().getLevel(magicPlayer), w - 20, posY + 30, -1);

                    if (isOnCooldown2) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount3, w - 20, posY + 16, -1);
                    }

                    RenderSystem.depthMask(true);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.enableDepthTest();
                    RenderSystem.disableBlend();
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                }
            }
        }
    }
}
