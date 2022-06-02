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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID)
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

                    RenderSystem.depthMask(false);
                    RenderSystem.enableBlend();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                    RenderSystem.setShaderColor(rgb, rgb, rgb + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getFirstSkill().getSkillIcon());
                    GuiComponent.blit(event.getMatrixStack(), posX + 180, posY + -76, 0, 0, 32, 32, 32, 32);

                    if (magic.getFirstSkill().getCooldownCount(entity) > 0) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount, posX + 183, posY + -66, -1);
                    }

                    RenderSystem.setShaderColor(rgb2, rgb2, rgb2 + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getSecondSkill().getSkillIcon());
                    GuiComponent.blit(event.getMatrixStack(), posX + 180, posY + -36, 0, 0, 32, 32, 32, 32);

                    if (magic.getSecondSkill().getCooldownCount(entity) > 0) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount2, posX + 183, posY + -26, -1);
                    }

                    RenderSystem.setShaderColor(rgb3, rgb3, rgb3 + 0.05F, 1);
                    RenderSystem.setShaderTexture(0, magic.getThirdSkill().getSkillIcon());
                    GuiComponent.blit(event.getMatrixStack(), posX + 180, posY + 4, 0, 0, 32, 32, 32, 32);

                    if (magic.getThirdSkill().getCooldownCount(entity) > 0) {
                        GuiComponent.drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, cooldownCount3, posX + 183, posY + 14, -1);
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
