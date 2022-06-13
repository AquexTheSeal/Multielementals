package com.aquextheseal.woe.registry;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.particle.CrystalSparkParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Multielementals.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MEParticleTypes {

    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Multielementals.MODID);

    public static final RegistryObject<SimpleParticleType> CRYSTAL_SPARK = PARTICLES.register("crystal_spark", () -> new SimpleParticleType(true));

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        engine.register(CRYSTAL_SPARK.get(), CrystalSparkParticle.Provider::new);
    }
}
