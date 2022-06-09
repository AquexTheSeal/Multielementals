package com.aquextheseal.woe.client.magiceffects;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.aquextheseal.woe.Multielementals;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CrystalSparkCrystalModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Multielementals.MODID, "crystal_spark_crystal"), "main");
	private final ModelPart crystalCircle;
	private float rotationValue;

	public CrystalSparkCrystalModel(ModelPart root) {
		this.crystalCircle = root.getChild("crystalCircle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition crystalCircle = partdefinition.addOrReplaceChild("crystalCircle", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition crystalFront = crystalCircle.addOrReplaceChild("crystalFront", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 22).addBox(-1.0F, 7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 13).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		PartDefinition crystalBack = crystalCircle.addOrReplaceChild("crystalBack", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, 0.0F, 24.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 22).addBox(-1.0F, 7.0F, 25.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 13).addBox(-1.0F, -14.0F, 25.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -7.0F, 23.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition crystalLeft = crystalCircle.addOrReplaceChild("crystalLeft", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 22).addBox(-1.0F, 7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 13).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition crystalRight = crystalCircle.addOrReplaceChild("crystalRight", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 22).addBox(-1.0F, 7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 13).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotationValue -= 0.04F;
		crystalCircle.yRot = -rotationValue;
		if (rotationValue <= -360) {
			rotationValue = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		crystalCircle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}