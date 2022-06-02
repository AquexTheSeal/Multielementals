package com.aquextheseal.woe.registry;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.gui.ElementMenuContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEContainerTypes {

    public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Multielementals.MODID);

    public static RegistryObject<MenuType<ElementMenuContainer>> ELEMENT_MENU = CONTAINERS.register("element_menu", () ->
            IForgeMenuType.create(ElementMenuContainer::new)
    );
}
