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
}
