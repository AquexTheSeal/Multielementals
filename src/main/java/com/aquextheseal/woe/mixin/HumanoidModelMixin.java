package com.aquextheseal.woe.mixin;

import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> {


    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Shadow protected abstract float rotlerpRad(float pAngle, float pMaxAngle, float pMul);

    @Shadow @Final public ModelPart leftLeg;

    @Shadow @Final public ModelPart rightLeg;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "TAIL"))
    private void setupSkillAnimations(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {

        if (pEntity instanceof Player player && pEntity instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {

                for (MagicSkill skill : magicPlayer.getMagicElement().skillsList()) {
                    skill.setupSkillAnimation(player, asHumanoidModel(this), pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                }
            }
        }
    }

    // Made this method because Java won't let me use "this" for cast.
    HumanoidModel<T> asHumanoidModel(AgeableListModel<T> model) {
        return (HumanoidModel<T>) model;
    }
}
