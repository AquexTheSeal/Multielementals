package com.aquextheseal.woe.magic.elements;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.magic.skills.fire.FlamingLeapSkill;

public class LightningElement extends MagicElement {

    @Override
    public String getElementRegistryName() {
        return "lightning";
    }

    @Override
    public MagicSkill getFirstSkill() {
        return new FlamingLeapSkill("test_lightning");
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
