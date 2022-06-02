package com.aquextheseal.woe.magic;

import com.aquextheseal.woe.magic.skilldata.MagicSkill;

public abstract class MagicElement {

    public MagicElement() {
    }

    public abstract String getElementRegistryName();

    public abstract MagicSkill getFirstSkill();

    public abstract MagicSkill getSecondSkill();

    public abstract MagicSkill getThirdSkill();

    public MagicSkill[] skillsList() {
        return new MagicSkill[]{getFirstSkill(), getSecondSkill(), getThirdSkill()};
    }
}
