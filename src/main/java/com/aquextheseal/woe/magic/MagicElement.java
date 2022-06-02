package com.aquextheseal.woe.magic;

import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.google.common.collect.ImmutableList;

public abstract class MagicElement {

    public MagicElement() {
    }

    public abstract String getElementRegistryName();

    public abstract MagicSkill getFirstSkill();

    public abstract MagicSkill getSecondSkill();

    public abstract MagicSkill getThirdSkill();

    public ImmutableList<MagicSkill> skillsList() {
        return ImmutableList.of(getFirstSkill(), getSecondSkill(), getThirdSkill());
    }
}
