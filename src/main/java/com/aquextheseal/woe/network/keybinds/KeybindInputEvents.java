package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.MENetwork;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeybindInputEvents {

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null) {
            if (WOEKeybindHandler.firstSkill.consumeClick()) {
                MENetwork.CHANNEL.sendToServer(new SkillPacket(0, key));
            }
            if (WOEKeybindHandler.secondSkill.consumeClick()) {
                MENetwork.CHANNEL.sendToServer(new SkillPacket(1, key));
            }
            if (WOEKeybindHandler.thirdSkill.consumeClick()) {
                MENetwork.CHANNEL.sendToServer(new SkillPacket(2, key));
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
