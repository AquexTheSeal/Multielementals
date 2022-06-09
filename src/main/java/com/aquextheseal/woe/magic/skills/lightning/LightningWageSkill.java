package com.aquextheseal.woe.magic.skills.lightning;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.MEAbilityUtil;
import com.aquextheseal.woe.util.MEDataUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class LightningWageSkill extends HoldableMagicSkill {

    public LightningWageSkill(String registryName) {
        super(registryName, MEMagicElements.LIGHTNING);
    }

    @Override
    public ResourceLocation getSkillIcon(Player caster) {
        return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/lightning_wage.png");
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", true);
        caster.getPersistentData().putBoolean(getRegistryName() + "isIncomplete", false);
        caster.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 2.5F);
    }

    @Override
    public void onRelease(Player caster, Level world) {
        if (caster.getPersistentData().getInt(getRegistryName() + "skillTimer") >= 40) {
            double d0 = -Mth.sin(caster.getYRot() * ((float)Math.PI / 180F));
            double d1 = Mth.cos(caster.getYRot() * ((float)Math.PI / 180F));
            Entity tracedEntity = MEAbilityUtil.getRayTracedEntity(caster, 8 + (getLevel((MagicPlayer) caster) / 8D));
            if (tracedEntity == null) {
                addBolt(caster, world, caster.getX() + d0 * 6, caster.getY(), caster.getZ() + d1 * 6);
            } else {
                addBolt(caster, world, tracedEntity.getX(), tracedEntity.getY(), tracedEntity.getZ());
            }
        } else {
            caster.getPersistentData().putBoolean(getRegistryName() + "isIncomplete", true);
        }

        caster.getPersistentData().putInt(getRegistryName() + "skillTimer", 0);
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", false);
    }

    public void addBolt(Player caster, Level world, double x, double y, double z) {
        if (getLevel((MagicPlayer) caster) >= 40) {
            world.explode(caster, x, y, z, getLevel((MagicPlayer) caster) / 25F, Explosion.BlockInteraction.BREAK);
        }
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(world);
        bolt.setDamage((float) getLevel((MagicPlayer) caster) / 4);
        bolt.moveTo(x, y, z);
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
                caster.playSound(SoundEvents.SHULKER_SHOOT, 0.5F, 3.4F);
                caster.playSound(SoundEvents.AXE_SCRAPE, 0.75F, 3.4F);
                if (world instanceof ServerLevel server) {
                    server.sendParticles(
                            ParticleTypes.CRIT, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
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
        return
                caster.getPersistentData().getBoolean(getRegistryName() + "isIncomplete") ?
                        60 : 500 - Mth.clamp((caster.experienceLevel / 2), 0, 150);
    }

    @Override
    public boolean shouldStopActionWhen(Player player) {
        return !player.getPersistentData().getBoolean(getRegistryName() + "holdingOn");
    }
}
