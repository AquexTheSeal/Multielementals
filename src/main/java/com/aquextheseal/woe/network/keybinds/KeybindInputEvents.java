package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.elementdata.OpenElementMenuPacket;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeybindInputEvents {

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null) {
            if (key == MEKeybindHandler.firstSkill.getKey().getValue()) {
                if (action == GLFW.GLFW_PRESS) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(0, key));
                } else if (action == GLFW.GLFW_RELEASE) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(0, key, true));
                }
            }

            if (key == MEKeybindHandler.secondSkill.getKey().getValue()) {
                if (action == GLFW.GLFW_PRESS) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(1, key));
                } else if (action == GLFW.GLFW_RELEASE) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(1, key, true));
                }
            }

            if (key == MEKeybindHandler.thirdSkill.getKey().getValue()) {
                if (action == GLFW.GLFW_PRESS) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(2, key));
                } else if (action == GLFW.GLFW_RELEASE) {
                    MENetwork.CHANNEL.sendToServer(new SkillPacket(2, key, true));
                }
            }

            if (MEKeybindHandler.openElementMenu.consumeClick()) {
                if (((MagicPlayer) mc.player).getMagicElement() != null) {
                    MENetwork.CHANNEL.sendToServer(new OpenElementMenuPacket());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClicked(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }
}
