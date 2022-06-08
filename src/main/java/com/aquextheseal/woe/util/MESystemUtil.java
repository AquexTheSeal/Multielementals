package com.aquextheseal.woe.util;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;

import javax.annotation.Nullable;

/**
 * Utility class to easily access my stupid dumb spaghetti code
 * - Aqu.
 */
public class MESystemUtil {

    @Nullable
    public static MagicElement getMagicElementWithString(String string) {
        for (MagicElement element : MEMagicElements.MAGIC_ELEMENTS.values()) {
            if (element.getElementRegistryName().equals(string)) {
                return element;
            }
        }
        return null;
    }

    public static int getLevelOfSkill(int skillIndex, MagicPlayer magicPlayer) {
        return getSkillIndex(skillIndex, magicPlayer).getLevel(magicPlayer);
    }

    public static MagicSkill getSkillIndex(int skillIndex, MagicPlayer magicPlayer) {
        return switch (skillIndex) {
            case 0 -> magicPlayer.getMagicElement().getFirstSkill();
            case 1 -> magicPlayer.getMagicElement().getSecondSkill();
            case 2 -> magicPlayer.getMagicElement().getThirdSkill();
            default -> null;
        };
    }

    public static void setSkillLevelOfIndex(int index, int value, MagicPlayer magicPlayer) {
        if (magicPlayer.getMagicElement() != null) {
            switch (index) {
                case 0 -> magicPlayer.setFirstSkillLevel(value);
                case 1 -> magicPlayer.setSecondSkillLevel(value);
                case 2 -> magicPlayer.setThirdSkillLevel(value);
            }
        }
    }
}
