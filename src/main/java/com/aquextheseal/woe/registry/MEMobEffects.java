package com.aquextheseal.woe.registry;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.mobeffects.MEEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEMobEffects {

    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Multielementals.MODID);

    public static RegistryObject<MobEffect> PARALYSIS = EFFECTS.register("paralysis", () -> (
            new MEEffect(MobEffectCategory.HARMFUL, 8171462))
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", -1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", -0.65F, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );
}
