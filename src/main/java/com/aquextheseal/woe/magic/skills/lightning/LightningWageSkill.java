package com.aquextheseal.woe.magic.skills.lightning;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.MEDataUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LightningWageSkill extends HoldableMagicSkill {

    public static boolean holdOn;
    public static int skillTimer;

    public LightningWageSkill(String registryName) {
        super(registryName, MEMagicElements.LIGHTNING);
    }

    @Override
    public ResourceLocation getSkillIcon() {
        return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/lightning_wage.png");
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", true);
    }

    @Override
    public void onRelease(Player caster, Level world) {
        if (caster.getPersistentData().getInt(getRegistryName() + "skillTimer") >= 40) {
            double d0 = -Mth.sin(caster.getYRot() * ((float)Math.PI / 180F));
            double d1 = Mth.cos(caster.getYRot() * ((float)Math.PI / 180F));
            addBolt(caster, world, d0 * 4, d1 * 4);

            if (getLevel((MagicPlayer) caster) >= 15) {
                addBolt(caster, world, -(d0 * 4), -(d1 * 4));
            }
            if (getLevel((MagicPlayer) caster) >= 35) {
                addBolt(caster, world, d0 * 4, -(d1 * 4));
                addBolt(caster, world, -(d0 * 4), d1 * 4);
            }
            caster.addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                            60 + (getLevel((MagicPlayer) caster) / 2),
                            1 + (getLevel((MagicPlayer) caster) / 20),
                            false, false, false)
            );
            if (world instanceof ServerLevel server) {
                server.sendParticles(
                        ParticleTypes.CAMPFIRE_COSY_SMOKE, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
                        10, 0.75F, 0.75F, 0.75F, 0.01
                );
            }
        }
        caster.getPersistentData().putInt(getRegistryName() + "skillTimer", 0);
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", false);
    }

    public void addBolt(Player caster, Level world, double xOff, double zOff) {
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(world);
        bolt.setDamage((float) getLevel((MagicPlayer) caster) / 4);
        bolt.moveTo(caster.getX() + xOff, caster.getY(), caster.getZ() + zOff);
        world.addFreshEntity(bolt);
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
                caster.playSound(SoundEvents.SHULKER_SHOOT, 1.0F, 3.4F);
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
        return skillTimer == 0;
    }
}
