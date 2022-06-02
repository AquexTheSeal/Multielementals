package com.aquextheseal.woe.magic.elements;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.magic.skills.fire.FlamingLeapSkill;

public class IceElement extends MagicElement {

    @Override
    public String getElementRegistryName() {
        return "ice";
    }

    @Override
    public MagicSkill getFirstSkill() {
        return new FlamingLeapSkill("test_ice");
    }

    @Override
    public MagicSkill getSecondSkill() {
        return null;
    }

    @Override
    public MagicSkill getThirdSkill() {
        return null;
    }
}
