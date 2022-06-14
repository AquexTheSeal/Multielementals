package com.aquextheseal.woe.mixin.accessor;

import net.minecraft.client.particle.SingleQuadParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SingleQuadParticle.class)
public interface SingleQuadParticleAccessor {

    @Invoker("getU0")
    float invokeGetU0();

    @Invoker("getU1")
    float invokeGetU1();

    @Invoker("getV0")
    float invokeGetV0();

    @Invoker("getV1")
    float invokeGetV1();
}
