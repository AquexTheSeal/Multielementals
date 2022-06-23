package com.aquextheseal.woe.magic.elements;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.magic.skills.lightning.CrystalSparkSkill;
import com.aquextheseal.woe.magic.skills.lightning.HandOfZeusSkill;
import com.aquextheseal.woe.magic.skills.lightning.LightningWageSkill;

public class LightningElement extends MagicElement {

    @Override
    public String getElementRegistryName() {
        return "lightning";
    }

    @Override
    public MagicSkill getFirstSkill() {
        return new LightningWageSkill("lightning_wage");
    }

    @Override
    public MagicSkill getSecondSkill() {
        return new CrystalSparkSkill("crystal_spark");
    }

    @Override
    public MagicSkill getThirdSkill() {
        return new HandOfZeusSkill("hand_of_zeus");
    }
}
