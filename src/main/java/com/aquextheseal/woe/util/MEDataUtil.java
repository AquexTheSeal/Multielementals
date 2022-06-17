package com.aquextheseal.woe.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class MEDataUtil {

    public static void addCompoundInt(CompoundTag tag, String name, int addition) {
        tag.putInt(name, tag.getInt(name) + addition);
    }

    public static void addCompoundInt(Player player, String name, int addition) {
        addCompoundInt(player.getPersistentData(), name, addition);
    }

    public static void addCompoundFloat(CompoundTag tag, String name, float addition) {
        tag.putFloat(name, tag.getFloat(name) + addition);
    }

    public static void addCompoundFloat(Player player, String name, float addition) {
        addCompoundFloat(player.getPersistentData(), name, addition);
    }
}
