package com.aquextheseal.woe.magic.elements;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.magic.skills.earth.EarthquakeSkill;
import com.aquextheseal.woe.magic.skills.fire.FlamingLeapSkill;
import com.aquextheseal.woe.magic.skills.lightning.LightningWageSkill;

public class EarthElement extends MagicElement {

    @Override
    public String getElementRegistryName() {
        return "earthquake";
    }

    @Override
    public final MagicSkill getFirstSkill() {
        return new EarthquakeSkill("earthquake");
    }

    @Override
    public final MagicSkill getSecondSkill() {
        return new FlamingLeapSkill("earthquake_test_1");
    }

    @Override
    public final MagicSkill getThirdSkill() {
        return new LightningWageSkill("earthquake_test_2");
    }
}
