package com.aquextheseal.woe.magic.skills.lightning;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.registry.MEMobEffects;
import com.aquextheseal.woe.util.MEDataUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HandOfZeusSkill extends MagicSkill {

    public HandOfZeusSkill(String registryName) {
        super(registryName, MEMagicElements.LIGHTNING);
    }

    @Override
    public ResourceLocation getSkillIcon(Player caster) {
        if (caster.getPersistentData().getBoolean("hasHandOfZeus")) {
            return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/hand_of_zeus_active.png");
        } else {
            return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/hand_of_zeus.png");
        }
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean("hasHandOfZeus", true);
    }

    public static void handleHit(Player player, LivingEntity target, Level world) {
        if (player.getPersistentData().getBoolean("hasHandOfZeus")) {
            target.hurt(DamageSource.LIGHTNING_BOLT, 3.0F);
            target.knockback(2F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
            target.addEffect(new MobEffectInstance(MEMobEffects.PARALYSIS.get(), 40, 0));
            if (world instanceof ServerLevel server) {
                server.sendParticles(
                        ParticleTypes.FIREWORK, target.getX(), target.getY() + 0.75D, target.getZ(),
                        25, 0.75F, 0.75F, 0.75F, 0.5
                );
            }
            if (target.getPersistentData().getInt("zeusCurseStack") < 5) {
                MEDataUtil.addCompoundInt(target.getPersistentData(), "zeusCurseStack", 1);
            }
            player.getPersistentData().putBoolean("hasHandOfZeus", false);
        }
    }

    @Override
    public void baseSkillTick(Player caster, Level world) {
        if (caster.getPersistentData().getBoolean("hasHandOfZeus")) {
        }
    }

    @Override
    public int getMaxCooldown(Player caster) {
        return 60;
    }

    @Override
    public int getExpenseMultiplier() {
        return 6;
    }
}
