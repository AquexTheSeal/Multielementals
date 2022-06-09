package com.aquextheseal.woe.client.magiceffects;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.magic.skills.lightning.CrystalSparkSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CrystalSparkCrystalLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation CRYSTAL_LOCATION = new ResourceLocation(Multielementals.MODID, "textures/entity/crystal_spark_crystal.png");
    private final CrystalSparkCrystalModel<T> crystalSparkCrystalModel;
    private float timer;

    public CrystalSparkCrystalLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet entityModelSet) {
        super(pRenderer);
        this.crystalSparkCrystalModel = new CrystalSparkCrystalModel<>(entityModelSet.bakeLayer(CrystalSparkCrystalModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity instanceof Player player && pLivingEntity instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {
                if (magicPlayer.getMagicElement().getSecondSkill() instanceof CrystalSparkSkill) {
                    if (!magicPlayer.getMagicElement().getSecondSkill().shouldStopActionWhen(player)) {
                        if (timer < 1.0F) {
                            timer += 0.0625;
                        }
                    } else {
                        if (timer > 0) {
                            timer -= 0.125;
                        }
                    }
                }
                if (timer > 0) {
                    ResourceLocation resourcelocation = getCrystalTexture(pLivingEntity);
                    pMatrixStack.pushPose();
                    pMatrixStack.translate(0.0D, 0.0D, 0.0D);
                    this.getParentModel().copyPropertiesTo(this.crystalSparkCrystalModel);
                    this.crystalSparkCrystalModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(pBuffer, RenderType.armorCutoutNoCull(resourcelocation), false, false);
                    this.crystalSparkCrystalModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, timer);
                    pMatrixStack.popPose();
                }
            }
        }
    }

    public ResourceLocation getCrystalTexture(T entity) {
        return CRYSTAL_LOCATION;
    }
}
