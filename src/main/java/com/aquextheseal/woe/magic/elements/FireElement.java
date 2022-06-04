package com.aquextheseal.woe.magic.elements;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.magic.skills.fire.FlamingLeapSkill;

public class FireElement extends MagicElement {

    @Override
    public String getElementRegistryName() {
        return "fire";
    }

    @Override
    public final MagicSkill getFirstSkill() {
        return new FlamingLeapSkill("flaming_leap");
    }

    @Override
    public final MagicSkill getSecondSkill() {
        return new FlamingLeapSkill("test_2nd");
    }

    @Override
    public final MagicSkill getThirdSkill() {
        return new FlamingLeapSkill("test_3rd");
    }
}
