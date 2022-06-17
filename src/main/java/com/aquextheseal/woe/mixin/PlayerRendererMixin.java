package com.aquextheseal.woe.mixin;

import com.aquextheseal.woe.client.magiceffects.CrystalSparkCrystalLayer;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    // Dummy Constructor
    public PlayerRendererMixin(EntityRendererProvider.Context pContext, PlayerModel<AbstractClientPlayer> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void registerAccessories(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci) {
        this.addLayer(new CrystalSparkCrystalLayer<>(this, pContext.getModelSet()));
    }

        @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At(value = "TAIL"))
    protected void setupRotations(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci) {
        if ( pEntityLiving instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {
                for (MagicSkill skill : magicPlayer.getMagicElement().skillsList()) {
                    skill.setupSkillRotation(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
                }
            }
        }
    }
}
