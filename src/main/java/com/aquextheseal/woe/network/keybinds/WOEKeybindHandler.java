package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.Multielementals;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class WOEKeybindHandler {

    public static KeyMapping firstSkill;

    public static void register(final FMLClientSetupEvent event) {
        firstSkill = create("first_skill", GLFW.GLFW_KEY_Z);
        ClientRegistry.registerKeyBinding(firstSkill);
    }

    private static KeyMapping create(String name,  int key) {
        return new KeyMapping("key." + Multielementals.MODID + "." + name, key, "key.category." +  Multielementals.MODID);
    }
}
