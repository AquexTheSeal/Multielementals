package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.elements.FireElement;
import com.aquextheseal.woe.magic.elements.LightningElement;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID)
public class PassiveEvents {

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() instanceof FireElement) {
                    if (event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE) {
                        event.setAmount(event.getAmount() / 1.65F);
                    }
                    if (event.getSource() == DamageSource.LAVA) {
                        event.setAmount(event.getAmount() / 1.35F);
                    }
                }
                if (magicPlayer.getMagicElement() instanceof LightningElement) {
                    if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                        event.setAmount(event.getAmount() / 1.45F);
                    }
                    if (event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.ON_FIRE) {
                        event.setAmount(event.getAmount() / 1.2F);
                    }
                }
            }
        }
    }
}
