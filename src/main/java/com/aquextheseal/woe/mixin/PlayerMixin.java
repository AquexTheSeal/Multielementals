package com.aquextheseal.woe.mixin;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.util.MEMechanicUtil;
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

    private static final EntityDataAccessor<Integer>
            FIRST_SKILL_LEVEL = createIntData(), SECOND_SKILL_LEVEL = createIntData(), THIRD_SKILL_LEVEL = createIntData(),
            FIRST_SKILL_CD = createIntData(), SECOND_SKILL_CD = createIntData(), THIRD_SKILL_CD = createIntData();

    // Dummy Constructor
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    @Nullable
    public MagicElement getMagicElement() {
        return MEMechanicUtil.getMagicElementWithString(this.entityData.get(ELEMENT));
    }

    @Override
    public void setMagicElement(MagicElement element) {
        this.entityData.set(ELEMENT, element.getElementRegistryName());
    }

    @Override
    public void setMagicElement(String element) {
        this.entityData.set(ELEMENT, element);
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

        this.entityData.define(FIRST_SKILL_LEVEL, 0);
        this.entityData.define(SECOND_SKILL_LEVEL, 0);
        this.entityData.define(THIRD_SKILL_LEVEL, 0);

        this.entityData.define(FIRST_SKILL_CD, 0);
        this.entityData.define(SECOND_SKILL_CD, 0);
        this.entityData.define(THIRD_SKILL_CD, 0);
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
        MagicElement byItElement = MEMechanicUtil.getMagicElementWithString(pCompound.getString("magicElement"));
        if (byItElement != null) {
            setMagicElement(byItElement);
        }
    }

    private static EntityDataAccessor<Integer> createIntData() {
        return SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    }

    @Override public int getFirstSkillLevel() { return this.entityData.get(FIRST_SKILL_LEVEL); }

    @Override public void setFirstSkillLevel(int value) { this.entityData.set(FIRST_SKILL_LEVEL, value); }

    @Override public int getSecondSkillLevel() { return this.entityData.get(SECOND_SKILL_LEVEL); }

    @Override public void setSecondSkillLevel(int value) { this.entityData.set(SECOND_SKILL_LEVEL, value); }

    @Override public int getThirdSkillLevel() { return this.entityData.get(THIRD_SKILL_LEVEL); }

    @Override public void setThirdSkillLevel(int value) { this.entityData.set(THIRD_SKILL_LEVEL, value); }

    @Override public int getFirstSkillCD() { return this.entityData.get(FIRST_SKILL_CD); }

    @Override public void setFirstSkillCD(int value) { this.entityData.set(FIRST_SKILL_CD, value); }

    @Override public int getSecondSkillCD() { return this.entityData.get(SECOND_SKILL_CD); }

    @Override public void setSecondSkillCD(int value) { this.entityData.set(SECOND_SKILL_CD, value); }

    @Override public int getThirdSkillCD() { return this.entityData.get(THIRD_SKILL_CD); }

    @Override public void setThirdSkillCD(int value) { this.entityData.set(THIRD_SKILL_CD, value); }


    private static ServerPlayer asServerPlayer(Entity entity) {
        return (ServerPlayer) entity;
    }

    private static boolean instanceOfServerPlayer(Entity entity) {
        return entity instanceof ServerPlayer;
    }
}
