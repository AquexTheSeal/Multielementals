package com.aquextheseal.woe.magic.skills.lightning;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HandOfZeusSkill extends MagicSkill {

    public HandOfZeusSkill(String registryName) {
        super(registryName, MEMagicElements.LIGHTNING);
    }

    @Override
    public ResourceLocation getSkillIcon(Player caster) {
        return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/hand_of_zeus.png");
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean("hasHandOfZeus", true);
    }

    public static void handleHit(Player player, LivingEntity target, Level world) {
        if (player.getPersistentData().getBoolean("hasHandOfZeus")) {
            target.hurt(DamageSource.LIGHTNING_BOLT, 3.0F);

            player.getPersistentData().putBoolean("hasHandOfZeus", false);
        }
    }

    @Override
    public int getMaxCooldown(Player caster) {
        return 60;
    }

    @Override
    public boolean shouldStopActionWhen(Player player) {
        return false;
    }

    @Override
    public int getExpenseMultiplier() {
        return 6;
    }
}
