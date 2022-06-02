package com.aquextheseal.woe.events;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Multielementals.MODID)
public class PlayerDataEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof MagicPlayer magicPlayer) {
            MagicElement playerElement = magicPlayer.getMagicElement();
            if (playerElement != null) {
                for (MagicSkill skill : playerElement.skillsList()) {
                    if (skill != null) {
                        if (skill.getCooldownCount(event.player) > 0) {
                            skill.setCooldownCount(event.player, skill.getCooldownCount(event.player) - 1);

                            if (magicPlayer.getCurrentSkillAction().equals(skill.getRegistryName()) && skill.shouldStopActionWhen(event.player)) {
                                magicPlayer.setCurrentSkillAction(MagicPlayer.EMPTY);
                            }
                        } else {
                            skill.noCDSkillTick(event.player, event.player.getLevel());
                        }
                    }
                }
            }
        }
    }

    // ForgeHook that triggers on player's cloning (Death triggers it too).
    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.Clone event) {

        // checks if the cause of clone was by death.
        if (event.isWasDeath()) {

            // Gets the magic element from the previous/original player before death.
            // event.getOriginal() = the original player before death.
            MagicElement element = ((MagicPlayer) event.getOriginal()).getMagicElement();

            // Sets the magic element of the cloned/respawned player with the magic element variable above.
            ((MagicPlayer) event.getPlayer()).setMagicElement(element);
        }
    }
}
