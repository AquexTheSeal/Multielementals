package com.aquextheseal.woe.magic.skills.lightning;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.registry.MEParticleTypes;
import com.aquextheseal.woe.util.MEDataUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CrystalSparkSkill extends HoldableMagicSkill {
    public final String chargeTime = getRegistryName() + "chargeTimer", shouldCharge = getRegistryName() + "shouldCharge";
    
    public CrystalSparkSkill(String registryName) {
        super(registryName, MEMagicElements.LIGHTNING);
    }

    @Override
    public ResourceLocation getSkillIcon(Player caster) {
        if (caster.getPersistentData().getBoolean(getRegistryName() + "holdingOn")) {
            return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/crystal_spark_charging.png");
        } else {
            return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/crystal_spark.png");
        }
    }

    @Override
    public void onExecution(Player caster, Level world) {
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", true);
        caster.getPersistentData().putBoolean(getRegistryName() + "isIncomplete", false);
        caster.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 2.5F);
    }

    @Override
    public void onRelease(Player caster, Level world) {
        if (caster.getPersistentData().getInt(getRegistryName() + "skillTimer") >= 20) {
            double dirX = Math.sin(Math.toRadians(caster.getYRot() + 180));
            double dirY = Math.sin(Math.toRadians(0 - caster.getXRot()));
            double dirZ = Math.cos(Math.toRadians(caster.getYRot()));
            caster.setDeltaMovement(dirX * 3, dirY * 3, dirZ * 3);
            caster.playSound(SoundEvents.SHULKER_SHOOT, 1.0F, 1.35F);
            caster.getPersistentData().putBoolean(shouldCharge, true);
            caster.getPersistentData().putInt(chargeTime, 20);
        } else {
            caster.getPersistentData().putBoolean(getRegistryName() + "isIncomplete", true);
        }

        caster.getPersistentData().putInt(getRegistryName() + "skillTimer", 0);
        caster.getPersistentData().putBoolean(getRegistryName() + "holdingOn", false);
    }

    @Override
    public void baseSkillTick(Player caster, Level world) {
        if (caster.getPersistentData().getBoolean(getRegistryName() + "holdingOn")) {
            caster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 10, false, false, false));
            if (world instanceof ServerLevel server) {
                server.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
                        10, 0.75F, 0.75F, 0.75F, -0.5
                );
            }
            MEDataUtil.addCompoundInt(caster, getRegistryName() + "skillTimer", 1);
        }
        if (caster.getPersistentData().getInt(chargeTime) <= 0 && caster.getPersistentData().getBoolean(shouldCharge)) {
            for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(6))) {
                if (entity != caster) {
                    entity.hurt(DamageSource.LIGHTNING_BOLT, 2.0F);
                    entity.knockback(1.2F, caster.getX() - entity.getX(), caster.getZ() - entity.getZ());
                }
            }
            caster.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 0.50F, 3.5F);
            caster.playSound(SoundEvents.ANVIL_BREAK, 0.75F, 0.5F);
            world.addParticle(MEParticleTypes.CRYSTAL_SPARK.get(),  caster.getX(), caster.getY() + 0.4D, caster.getZ(), 0, 0, 0);
            if (world instanceof ServerLevel server) {
                server.sendParticles(
                        ParticleTypes.FIREWORK, caster.getX(), caster.getY() + 0.15D, caster.getZ(),
                        75, 1F, 0.5F, 1F, 0.01
                );
                server.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK, caster.getX(), caster.getY() + 0.15D, caster.getZ(),
                        75, 1F, 0.5F, 1F, 0.01
                );
            }
            caster.getPersistentData().putBoolean(shouldCharge, false);
        }
        if (caster.getPersistentData().getInt(chargeTime) > 0) {
            MEDataUtil.addCompoundInt(caster, chargeTime, -1);
        }
    }

    @Override
    public <T extends LivingEntity> void setupSkillAnimation(Player player, HumanoidModel<T> model, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (player.getPersistentData().getBoolean(getRegistryName() + "holdingOn")) {
            model.body.xRot = Mth.sin(pAgeInTicks * 0.075F) * 0.15F + 0.35F;
            model.rightArm.y = Mth.cos(pAgeInTicks * 0.025F) * 0.12F + 0.9F;
            model.leftArm.y = Mth.cos(pAgeInTicks * 0.025F) * 0.09F + 0.85F;
            model.leftArm.xRot = Mth.sin(pAgeInTicks * 0.075F) * 0.24F - 0.45F;
            model.rightArm.xRot = Mth.sin(pAgeInTicks * 0.075F) * 0.22F - 0.5F;
            model.leftLeg.xRot = Mth.cos(pAgeInTicks * 0.105F) * 0.12F + 0.45F;
            model.rightLeg.xRot = Mth.cos(pAgeInTicks * 0.115F) * 0.18F + 0.95F;
            model.rightLeg.z = Mth.cos(pAgeInTicks * 0.105F) * 0.12F - 6.05F;
            model.rightLeg.y = Mth.cos(pAgeInTicks * 0.105F) * 0.12F + 0.15F;
        }
    }

    @Override
    public void setupSkillRotation(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        float rotval = pEntityLiving.getPersistentData().getFloat(getRegistryName() + "rotValue");

        if (pEntityLiving.getPersistentData().getBoolean(getRegistryName() + "holdingOn")) {
            MEDataUtil.addCompoundFloat(pEntityLiving, getRegistryName() + "rotValue", rotval <= 0.25F * 10 ? ((0.25F - rotval) / 4) / 2 : 0);
            pMatrixStack.mulPose(Vector3f.XP.rotation(rotval));
        } else pEntityLiving.getPersistentData().putFloat(getRegistryName() + "rotValue", 0);

        if (pEntityLiving.getPersistentData().getInt(getRegistryName() + "chargeTimer") > 0) {
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F - pEntityLiving.getXRot()));
            pMatrixStack.translate(0.0D, -1.0D, 0.3D);
        }
    }

    @Override
    public int getExpenseMultiplier() {
        return 2;
    }

    @Override
    public int getMaxCooldown(Player caster) {
        return
                caster.getPersistentData().getBoolean(getRegistryName() + "isIncomplete") ?
                        60 : 300 - Mth.clamp((caster.experienceLevel / 2), 0, 150);
    }
}
