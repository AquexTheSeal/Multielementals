package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.Multielementals;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class WOEKeybindHandler {

    public static KeyMapping firstSkill, secondSkill, thirdSkill;

    public static void register(final FMLClientSetupEvent event) {
        firstSkill = create("first_skill", GLFW.GLFW_KEY_Z);
        ClientRegistry.registerKeyBinding(firstSkill);
        secondSkill = create("second_skill", GLFW.GLFW_KEY_X);
        ClientRegistry.registerKeyBinding(secondSkill);
        thirdSkill = create("third_skill", GLFW.GLFW_MOUSE_BUTTON_MIDDLE);
        ClientRegistry.registerKeyBinding(thirdSkill);
    }

    private static KeyMapping create(String name,  int key) {
        return new KeyMapping("key." + Multielementals.MODID + "." + name, key, "key.category." +  Multielementals.MODID);
    }
}
