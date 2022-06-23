package com.aquextheseal.woe.network.elementdata;

import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PauseGamePacket {

    public PauseGamePacket() {
    }

    public static void encode(PauseGamePacket message, FriendlyByteBuf buffer) {
    }

    public static PauseGamePacket decode(FriendlyByteBuf buffer) {
        return new PauseGamePacket();
    }

    public static void execute(PauseGamePacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();

            if (player instanceof MagicPlayer magicPlayer) {
                for (MagicSkill element : magicPlayer.getMagicElement().skillsList()) {
                    if (element instanceof HoldableMagicSkill holdable) {
                        holdable.release(player, world);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
