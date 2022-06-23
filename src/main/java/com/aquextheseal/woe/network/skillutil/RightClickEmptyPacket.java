package com.aquextheseal.woe.network.skillutil;

import com.aquextheseal.woe.events.SkillEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RightClickEmptyPacket {

    public RightClickEmptyPacket() {
    }

    public static void encode(RightClickEmptyPacket message, FriendlyByteBuf buffer) {
    }

    public static RightClickEmptyPacket decode(FriendlyByteBuf buffer) {
        return new RightClickEmptyPacket();
    }

    public static void execute(RightClickEmptyPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            SkillEvents.onRightSwing(player, player.getCommandSenderWorld());
            context.setPacketHandled(true);
        });
    }
}
