package com.aquextheseal.woe.magic.skills.fire;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class FlamingLeapSkill extends MagicSkill {

    public FlamingLeapSkill(String registryName) {
        super(registryName, MEMagicElements.FIRE);
    }

    @Override
    public ResourceLocation getSkillIcon(Player caster) {
        return new ResourceLocation(Multielementals.MODID, "textures/gui/skills/flaming_leap.png");
    }

    @Override
    public void onExecution(Player caster, Level world) {
        MagicPlayer magicPlayer = (MagicPlayer) caster;

        caster.playSound(SoundEvents.FIRE_EXTINGUISH, 1F, 1F);
        caster.playSound(SoundEvents.SHULKER_SHOOT, 1F, 1F);
        caster.setDeltaMovement(caster.getDeltaMovement().add(0, 0.5 + (float) (getLevel(magicPlayer) / 25), 0));

        caster.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40 + (getLevel(magicPlayer) / 2), 0));
        if (!world.getBlockState(caster.blockPosition().below()).isAir()) {
            world.setBlock(caster.blockPosition(), Blocks.FIRE.defaultBlockState(), 2);
        }

        if (world instanceof ServerLevel server) {
            server.sendParticles(
                    ParticleTypes.FLAME, caster.getX(), caster.getY(), caster.getZ(),
                    25, 0.85F, 0.85F, 0.85F, 0.01
            );
        }
    }

    @Override
    public void noCDSkillTick(Player caster, Level world) {
        super.noCDSkillTick(caster, world);

        if (world instanceof ServerLevel server) {
            server.sendParticles(
                    ParticleTypes.FLAME, caster.getX(), caster.getY() + 0.75D, caster.getZ(),
                    2, 0.25F, 0.25F, 0.25F, 0.01
            );
        }
    }

    @Override
    public int getExpenseMultiplier() {
        return 1;
    }

    @Override
    public int getMaxCooldown(Player caster) {
        return 300 - Mth.clamp((caster.experienceLevel / 2), 0, 150);
    }

    @Override
    public boolean shouldStopActionWhen(Player player) {
        return player.isOnGround();
    }
}
