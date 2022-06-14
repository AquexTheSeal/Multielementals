package com.aquextheseal.woe.particle;

import com.aquextheseal.woe.util.MERenderUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalSparkParticle extends TextureSheetParticle {
    ParticleRenderType PARTICLE_SHEET_NON_FACING_LIT = new ParticleRenderType() {
        public void begin(BufferBuilder p_107462_, TextureManager p_107463_) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            p_107462_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator p_107465_) {
            p_107465_.end();
        }

        public String toString() {
            return "PARTICLE_SHEET_NON_FACING_LIT";
        }
    };
    private final SpriteSet sprites;

    CrystalSparkParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.sprites = pSprites;
        this.lifetime = 40;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 1.0F;
        this.setSpriteFromAge(pSprites);
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public void tick() {
        if (alpha > 0.0F) {
            alpha -= 0.05F;
        }
        if (quadSize < 5.0F) {
            this.quadSize += 0.25F / (quadSize * 0.75F);
        }
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    public ParticleRenderType getRenderType() {
        return PARTICLE_SHEET_NON_FACING_LIT;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        MERenderUtil.renderNonFacingParticle(this, pBuffer, pRenderInfo, pPartialTicks);
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CrystalSparkParticle(pLevel, pX, pY, pZ, pXSpeed, this.sprites);
        }
    }
}
