package com.aquextheseal.woe.mixin.accessor;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface ParticleAccessor {

    @Invoker("getLightColor")
    int invokeGetLightColor(float pPartialTick);
}
