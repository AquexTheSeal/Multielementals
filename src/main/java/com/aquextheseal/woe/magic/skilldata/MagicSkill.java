package com.aquextheseal.woe.magic.skilldata;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.keybinds.SkillClientPacket;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
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

    public abstract ResourceLocation getSkillIcon(Player caster);

    public abstract void onExecution(Player caster, Level world);

    public abstract int getMaxCooldown(Player caster);

    public abstract boolean shouldStopActionWhen(Player player);

    public abstract int getExpenseMultiplier();

    public final MagicElement getElement() {
        return element;
    }

    public final String getTranslationKey() {
        return "skill." + Multielementals.MODID + "." + getRegistryName();
    }

    public final String getDescriptionKey() {
        return getTranslationKey() + ".desc";
    }

    public void noCDSkillTick(Player caster, Level world) {
    }

    public void baseSkillTick(Player caster, Level world) {
    }

    public <T extends LivingEntity> void setupSkillAnimation(Player player, HumanoidModel<T> model, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    }

    public void setupSkillRotation(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
    }

    public int getLevel(MagicPlayer magicPlayer) {
        if (matchSkillSlot(getElement().getFirstSkill())) {
            return magicPlayer.getFirstSkillLevel();
        }
        if (matchSkillSlot(getElement().getSecondSkill())) {
            return magicPlayer.getSecondSkillLevel();
        }
        if (matchSkillSlot(getElement().getThirdSkill())) {
            return magicPlayer.getThirdSkillLevel();
        }
        return 0;
    }

    public int getCooldownCount(Player entity) {

        if (matchSkillSlot(getElement().getFirstSkill())) {
            return ((MagicPlayer) entity).getFirstSkillCD();
        }
        if (matchSkillSlot(getElement().getSecondSkill())) {
            return ((MagicPlayer) entity).getSecondSkillCD();
        }
        if (matchSkillSlot(getElement().getThirdSkill())) {
            return ((MagicPlayer) entity).getThirdSkillCD();
        }
        return 0;
    }

    public void setCooldownCount(Player entity, int value) {

        if (entity instanceof ServerPlayer serverPlayer) {

            if (matchSkillSlot(getElement().getFirstSkill())) ((MagicPlayer) serverPlayer).setFirstSkillCD(value);

            if (matchSkillSlot(getElement().getSecondSkill())) ((MagicPlayer) serverPlayer).setSecondSkillCD(value);

            if (matchSkillSlot(getElement().getThirdSkill())) ((MagicPlayer) serverPlayer).setThirdSkillCD(value);
        }
    }

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
            this.setCooldownCount(caster, getMaxCooldown(caster));
        }
    }

    public boolean matchSkillSlot(MagicSkill skill) {
        return skill.getRegistryName().equals(this.getRegistryName());
    }
}
