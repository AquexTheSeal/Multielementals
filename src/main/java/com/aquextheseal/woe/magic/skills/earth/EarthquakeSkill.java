package com.aquextheseal.woe.magic.skills.earth;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.MEDataUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EarthquakeSkill extends MagicSkill {

    public EarthquakeSkill(String registryName) {
        super(registryName, MEMagicElements.EARTH);
    }

    @Override
    public ResourceLocation getSkillIcon() {
        return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/earthquake.png");
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", true);
    }

    @Override
    public void baseSkillTick(Player caster, Level world) {
        if (caster.getPersistentData().getBoolean(getRegistryName() + "holdingOn")) {

            caster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 3, false, false, false));
            if (world instanceof ServerLevel server) {
                server.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
                        10, 0.75F, 0.75F, 0.75F, -0.5
                );
            }

            MEDataUtil.addCompoundInt(caster, getRegistryName() + "soundTimer", 1);
            if (caster.getPersistentData().getInt(getRegistryName() + "soundTimer") > 20) {
                caster.playSound(SoundEvents.SHULKER_SHOOT, 0.5F, 3.4F);
                caster.playSound(SoundEvents.AXE_SCRAPE, 0.75F, 3.4F);
                if (world instanceof ServerLevel server) {
                    server.sendParticles(
                            ParticleTypes.POOF, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
                            25, 0.75F, 0.75F, 0.75F, -0.01
                    );
                }
                caster.getPersistentData().putInt(getRegistryName() + "soundTimer", 0);
            }

            MEDataUtil.addCompoundInt(caster, getRegistryName() + "skillTimer", 1);
        }
    }

    @Override
    public int getExpenseMultiplier() {
        return 3;
    }

    @Override
    public int getMaxCooldown(Player caster) {
        return 500 - Mth.clamp((caster.experienceLevel / 2), 0, 150);
    }

    @Override
    public boolean shouldStopActionWhen(Player player) {
        return !player.getPersistentData().getBoolean(getRegistryName() + "holdingOn");
    }
}
