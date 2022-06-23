package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skills.lightning.HandOfZeusSkill;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.skillutil.RightClickEmptyPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID)
public class SkillEvents {

    @SubscribeEvent
    public static void onHitEntity(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            HandOfZeusSkill.handleHit(player, event.getEntityLiving(), event.getEntity().getLevel());
        }
    }

    public static void onRightSwing(Player player, Level world) {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
    }

    @SubscribeEvent
    public static void onRightSwingClient(PlayerInteractEvent.RightClickEmpty event) {
        MENetwork.CHANNEL.sendToServer(new RightClickEmptyPacket());
        onRightSwing(event.getPlayer(), event.getWorld());
    }
}
