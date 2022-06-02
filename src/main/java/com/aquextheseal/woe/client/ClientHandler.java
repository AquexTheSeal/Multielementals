package com.aquextheseal.woe.client;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.keybinds.WOEKeybindHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    @SubscribeEvent
    public static void onClientRegistry(FMLClientSetupEvent event) {
        WOEKeybindHandler.register(event);
    }
}
