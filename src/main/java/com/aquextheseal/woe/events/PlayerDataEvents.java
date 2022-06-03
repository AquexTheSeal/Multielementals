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
                        skill.baseSkillTick(event.player, event.player.getLevel());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {

        if (event.isWasDeath()) {
            MagicPlayer magicOriginal = (MagicPlayer) event.getOriginal();
            MagicPlayer magicPlayer = (MagicPlayer) event.getPlayer();

            MagicElement element = magicOriginal.getMagicElement();
            magicPlayer.setMagicElement(element);

            int level = magicOriginal.getFirstSkillLevel(); magicPlayer.setFirstSkillLevel(level);
            int level1 = magicOriginal.getSecondSkillLevel(); magicPlayer.setSecondSkillLevel(level1);
            int level2 = magicOriginal.getThirdSkillLevel(); magicPlayer.setThirdSkillLevel(level2);
        }
    }
}
