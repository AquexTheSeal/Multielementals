package com.aquextheseal.woe.network.elementdata;

import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSkillLevelPacket {

    public int index;
    public int value;

    public SetSkillLevelPacket() {
    }

    public SetSkillLevelPacket(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public static void encode(SetSkillLevelPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
    }

    public static SetSkillLevelPacket decode(FriendlyByteBuf buffer) {
        return new SetSkillLevelPacket(buffer.readInt(), buffer.readInt());
    }

    public static void execute(SetSkillLevelPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();

            if (player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() != null) {
                    switch (message.index) {
                        case 0: magicPlayer.setFirstSkillLevel(message.value);
                        case 1: magicPlayer.setSecondSkillLevel(message.value);
                        case 2: magicPlayer.setThirdSkillLevel(message.value);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
