package com.aquextheseal.woe.registry;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.elements.FireElement;
import com.aquextheseal.woe.magic.elements.IceElement;
import com.aquextheseal.woe.magic.elements.LightningElement;

import java.util.LinkedHashMap;

public class MEMagicElements {

    public static final LinkedHashMap<String, MagicElement> MAGIC_ELEMENTS = new LinkedHashMap<>();

    public static final MagicElement FIRE = register("fire", new FireElement());
    public static final MagicElement ICE = register("ice", new IceElement());
    public static final MagicElement LIGHTNING = register("lightning", new LightningElement());
    public static final MagicElement AIR = register("air", new LightningElement());
    public static final MagicElement EARTH = register("earth", new LightningElement());
    public static final MagicElement WATER = register("water", new LightningElement());

    public static MagicElement register(String name, MagicElement element) {
        MAGIC_ELEMENTS.put(name, element);
        return element;
    }
}
