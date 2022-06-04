package com.aquextheseal.woe.magic.skilldata;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.keybinds.SkillClientPacket;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class HoldableMagicSkill extends MagicSkill {

    public HoldableMagicSkill(String registryName, MagicElement element) {
        super(registryName, element);
    }

    public abstract void onRelease(Player caster, Level world);

    public void release(Player caster, Level world) {
        if (this.getCooldownCount(caster) == 0) {
            this.onRelease(caster, world);
            if (caster instanceof ServerPlayer serverPlayer && serverPlayer instanceof MagicPlayer magicPlayer) {
                magicPlayer.setCurrentSkillAction(getRegistryName());

                if (matchSkillSlot(getElement().getFirstSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(0, element.getElementRegistryName(), true));
                }

                if (matchSkillSlot(getElement().getSecondSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(1, element.getElementRegistryName(), true));
                }

                if (matchSkillSlot(getElement().getThirdSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(2, element.getElementRegistryName(), true));
                }
            }
            this.setCooldownCount(caster, getMaxCooldown(caster));
        }
    }

    @Override
    public void execute(Player caster, Level world) {
        if (this.getCooldownCount(caster) == 0) {
            this.onExecution(caster, world);
            if (caster instanceof ServerPlayer serverPlayer && serverPlayer instanceof MagicPlayer magicPlayer) {
                magicPlayer.setCurrentSkillAction(getRegistryName());

                if (matchSkillSlot(getElement().getFirstSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(0, element.getElementRegistryName()));
                }

                if (matchSkillSlot(getElement().getSecondSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(1, element.getElementRegistryName()));
                }

                if (matchSkillSlot(getElement().getThirdSkill())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new SkillClientPacket(2, element.getElementRegistryName()));
                }
            }
        }
    }
}
