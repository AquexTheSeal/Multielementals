package com.aquextheseal.woe.client;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.gui.ElementMenuScreen;
import com.aquextheseal.woe.network.keybinds.MEKeybindHandler;
import com.aquextheseal.woe.registry.MEContainerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onClientRegistry(FMLClientSetupEvent event) {
        MEKeybindHandler.register(event);

        event.enqueueWork(() -> {
            MenuScreens.register(MEContainerTypes.ELEMENT_MENU.get(), ElementMenuScreen::new);
        });
    }
}
