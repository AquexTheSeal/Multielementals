package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skills.lightning.LightningWageSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, value = Dist.CLIENT)
public class SpecialRenderEvents {
    private static float lwRotVal;

    private static float lwHandRotVal;

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
        event.getPoseStack().pushPose();
        if (event.getPlayer() instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {
                if (magicPlayer.getMagicElement().getFirstSkill() instanceof LightningWageSkill) {
                    if (!magicPlayer.getMagicElement().getFirstSkill().shouldStopActionWhen(event.getPlayer())) {
                        lwRotVal += 0.05F;
                        if (lwRotVal >= 360) {
                            lwRotVal = 0;
                        }
                        event.getPoseStack().mulPose(Vector3f.YP.rotation(lwRotVal));
                    } else {
                        clearPose(event.getPoseStack());
                    }
                }
            } else {
                clearPose(event.getPoseStack());
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void renderHand(RenderHandEvent event) {
        if (event.getHand() == InteractionHand.MAIN_HAND) {
            if (Minecraft.getInstance().player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() != null) {
                    if (magicPlayer.getMagicElement().getFirstSkill() instanceof LightningWageSkill) {
                        if (!magicPlayer.getMagicElement().getFirstSkill().shouldStopActionWhen(Minecraft.getInstance().player)) {
                            lwHandRotVal += lwHandRotVal <= 0.30F ? ((0.15 - lwHandRotVal) / 8) / 2 : 0;
                            event.getPoseStack().mulPose(Vector3f.XP.rotation(lwHandRotVal));
                            event.getPoseStack().translate(0, lwHandRotVal, 0);
                        } else {
                            clearPose(event.getPoseStack());
                        }
                    }
                } else {
                    clearPose(event.getPoseStack());
                }
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post event) {
        event.getPoseStack().popPose();
    }

    public static void clearPose(PoseStack stack) {
        lwRotVal = 0;
        lwHandRotVal = 0;
        stack.mulPose(Vector3f.XP.rotationDegrees(0));
        stack.mulPose(Vector3f.YP.rotationDegrees(0));
        stack.mulPose(Vector3f.ZP.rotationDegrees(0));
        stack.translate(0, 0, 0);
    }
}
