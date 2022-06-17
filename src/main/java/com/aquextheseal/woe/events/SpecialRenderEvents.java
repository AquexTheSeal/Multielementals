package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, value = Dist.CLIENT)
public class SpecialRenderEvents {

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void renderHand(final RenderHandEvent event) {
        LocalPlayer client = Minecraft.getInstance().player;
        if (client instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {
                for (MagicSkill skill : magicPlayer.getMagicElement().skillsList()) {
                    if (client.getPersistentData().getBoolean("lightning_wage" + "holdingOn")) {
                        if (event.getHand() == InteractionHand.MAIN_HAND) {
                            event.getPoseStack().mulPose(Vector3f.XP.rotation(0.50F));
                            event.getPoseStack().translate(0, 0.50F, 0);
                        }
                    }
                }
            } else {
                clearPose(event.getPoseStack());
            }
        }
    }

    public static void clearPose(PoseStack stack) {
        stack.mulPose(Vector3f.XP.rotationDegrees(0));
        stack.mulPose(Vector3f.YP.rotationDegrees(0));
        stack.mulPose(Vector3f.ZP.rotationDegrees(0));
        stack.translate(0, 0, 0);
    }
}
