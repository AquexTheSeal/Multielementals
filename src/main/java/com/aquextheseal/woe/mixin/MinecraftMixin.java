package com.aquextheseal.woe.mixin;

import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.network.MENetwork;
import com.aquextheseal.woe.network.elementdata.PauseGamePacket;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Nullable public ClientLevel level;

    @Shadow @Nullable public abstract ClientPacketListener getConnection();

    @Inject(method = "pauseGame", at = @At(value = "TAIL"))
    public void handlePauseGame(boolean pPauseOnly, CallbackInfo ci) {
        if (player instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() != null) {
                for (MagicSkill element : magicPlayer.getMagicElement().skillsList()) {
                    if (element instanceof HoldableMagicSkill holdable) {
                        holdable.release(player, level);
                    }
                }
            }
        }
        if (getConnection() != null) {
            MENetwork.CHANNEL.sendToServer(new PauseGamePacket());
        }
    }
}
