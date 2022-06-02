package com.aquextheseal.woe.util;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.registry.MEMagicElements;

import javax.annotation.Nullable;

public class MagicElementUtil {

    @Nullable
    public static MagicElement getMagicElementWithString(String string) {
        for (MagicElement element : MEMagicElements.MAGIC_ELEMENTS.values()) {
            if (element.getElementRegistryName().equals(string)) {
                return element;
            }
        }
        return null;
    }
}
