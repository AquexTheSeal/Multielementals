package com.aquextheseal.woe.util;

import com.aquextheseal.woe.mixin.accessor.ParticleAccessor;
import com.aquextheseal.woe.mixin.accessor.SingleQuadParticleAccessor;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MERenderUtil {

    /**
     * Override Particle#render with this method if you want to use it.
     */
    public static void renderNonFacingParticle(SingleQuadParticle particle, VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks, float xRot) {
        Vec3 vec3 = pRenderInfo.getPosition();
        float f = (float)(Mth.lerp(pPartialTicks, particle.xo, particle.x) - vec3.x());
        float f1 = (float)(Mth.lerp(pPartialTicks, particle.yo, particle.y) - vec3.y());
        float f2 = (float)(Mth.lerp(pPartialTicks, particle.zo, particle.z) - vec3.z());
        Quaternion quaternion;

        quaternion = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
        quaternion.mul(Vector3f.XP.rotation(xRot));

        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = particle.getQuadSize(pPartialTicks);
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }
        float f7 = ((SingleQuadParticleAccessor) particle).invokeGetU0();
        float f8 = ((SingleQuadParticleAccessor) particle).invokeGetU1();
        float f5 = ((SingleQuadParticleAccessor) particle).invokeGetV0();
        float f6 = ((SingleQuadParticleAccessor) particle).invokeGetV1();
        int j = ((ParticleAccessor) particle).invokeGetLightColor(pPartialTicks);
        pBuffer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(f8, f6).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(j).endVertex();
        pBuffer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(f8, f5).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(j).endVertex();
        pBuffer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(f7, f5).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(j).endVertex();
        pBuffer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(f7, f6).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(j).endVertex();
    }

    public static void renderNonFacingParticle(SingleQuadParticle particle, VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        renderNonFacingParticle(particle, pBuffer, pRenderInfo, pPartialTicks, 1.635F);
        renderNonFacingParticle(particle, pBuffer, pRenderInfo, pPartialTicks, -1.635F);
    }
}
