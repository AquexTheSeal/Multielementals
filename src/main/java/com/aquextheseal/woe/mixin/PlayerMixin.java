package com.aquextheseal.woe.mixin;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.util.MagicElementUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements MagicPlayer {
    private static final EntityDataAccessor<String> ELEMENT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> MAGIC_ACTION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.STRING);

    // Dummy Constructor
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    @Nullable
    public MagicElement getMagicElement() {
        return MagicElementUtil.getMagicElementWithString(this.entityData.get(ELEMENT));
    }

    @Override
    public void setMagicElement(MagicElement element) {
        this.entityData.set(ELEMENT, element.getElementRegistryName());
    }

    @Override
    public String getCurrentSkillAction() {
        return this.entityData.get(MAGIC_ACTION);
    }

    @Override
    public void setCurrentSkillAction(String skillAction) {
        this.entityData.set(MAGIC_ACTION, skillAction);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void defineDataAccessors(CallbackInfo ci) {
        this.entityData.define(ELEMENT, MagicPlayer.EMPTY);
        this.entityData.define(MAGIC_ACTION, MagicPlayer.EMPTY);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addData(CompoundTag pCompound, CallbackInfo ci) {
        MagicElement element = getMagicElement();
        if (element != null) {
            pCompound.putString("magicElement", element.getElementRegistryName());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void loadData(CompoundTag pCompound, CallbackInfo ci) {
        MagicElement byItElement = MagicElementUtil.getMagicElementWithString(pCompound.getString("magicElement"));
        if (byItElement != null) {
            setMagicElement(byItElement);
        }
    }

    private static ServerPlayer asServerPlayer(Entity entity) {
        return (ServerPlayer) entity;
    }

    private static boolean instanceOfServerPlayer(Entity entity) {
        return entity instanceof ServerPlayer;
    }
}
