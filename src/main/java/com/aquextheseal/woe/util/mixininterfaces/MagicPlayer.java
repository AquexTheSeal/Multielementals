package com.aquextheseal.woe.util.mixininterfaces;

import com.aquextheseal.woe.magic.MagicElement;

import javax.annotation.Nullable;

public interface MagicPlayer {
    String EMPTY = "empty";

    @Nullable
    MagicElement getMagicElement();

    void setMagicElement(MagicElement element);

    String getCurrentSkillAction();

    void setCurrentSkillAction(String skillAction);
}
