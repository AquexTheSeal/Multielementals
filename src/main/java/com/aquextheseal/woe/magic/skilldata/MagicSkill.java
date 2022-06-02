package com.aquextheseal.woe.magic.skilldata;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.keybinds.FirstSkillClientPacket;
import com.aquextheseal.woe.network.magicdata.SetSkillCooldownClientPacket;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class MagicSkill {

    public final MagicElement element;
    public final String registryName;

    public MagicSkill(String registryName, MagicElement element) {
        this.registryName = registryName;
        this.element = element;
    }

    public String getRegistryName() {
        return registryName;
    }

    public abstract ResourceLocation getSkillIcon();

    public abstract void onExecution(Player caster, Level world);

    public abstract int getMaxCooldown(Player caster);

    public abstract boolean shouldStopActionWhen(Player player);

    public final MagicElement getElement() {
        return element;
    }

    public void noCDSkillTick(Player caster, Level world) {
    }

    public int getCooldownCount(Player entity) {
        return entity.getPersistentData().getInt(getRegistryName() + "cooldown");
    }

    public void setCooldownCount(Player entity, int value) {
        entity.getPersistentData().putInt(getRegistryName() + "cooldown", value);
        if (entity instanceof ServerPlayer serverPlayer) {

            if (getElement().getFirstSkill().getRegistryName().equals(getRegistryName())) {
                MENetwork.sendPacketToPlayer(serverPlayer, new SetSkillCooldownClientPacket(0, value));

            } else if (getElement().getSecondSkill().getRegistryName().equals(getRegistryName())) {
                MENetwork.sendPacketToPlayer(serverPlayer, new SetSkillCooldownClientPacket(1, value));

            } else if (getElement().getThirdSkill().getRegistryName().equals(getRegistryName())) {
                MENetwork.sendPacketToPlayer(serverPlayer, new SetSkillCooldownClientPacket(2, value));
            }
        }
    }

    public void setCooldownCountForClient(Player entity, int value) {
        entity.getPersistentData().putInt(getRegistryName() + "cooldown", value);
    }

    public void execute(Player caster, Level world) {
        if (this.getCooldownCount(caster) == 0) {
            this.onExecution(caster, world);
            if (caster instanceof ServerPlayer serverPlayer && serverPlayer instanceof MagicPlayer magicPlayer) {
                magicPlayer.setCurrentSkillAction(getRegistryName());

                if (getElement().getFirstSkill().getRegistryName().equals(getRegistryName())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new FirstSkillClientPacket(0, getElement().getElementRegistryName()));

                } else if (getElement().getSecondSkill().getRegistryName().equals(getRegistryName())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new FirstSkillClientPacket(1, getElement().getElementRegistryName()));

                } else if (getElement().getThirdSkill().getRegistryName().equals(getRegistryName())) {
                    MENetwork.sendPacketToPlayer(serverPlayer, new FirstSkillClientPacket(2, getElement().getElementRegistryName()));
                }
            }
            this.setCooldownCount(caster, getMaxCooldown(caster));
        }
    }
}
