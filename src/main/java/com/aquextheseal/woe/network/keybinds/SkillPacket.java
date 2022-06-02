package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillPacket {

    public int index;
    public int key;

    public SkillPacket() {
    }

    public SkillPacket(int index, int key) {
        this.index = index;
        this.key = key;
    }

    public static void encode(SkillPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeInt(message.key);
    }

    public static SkillPacket decode(FriendlyByteBuf buffer) {
        return new SkillPacket(buffer.readInt(), buffer.readInt());
    }

    public static void execute(SkillPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();
            int index = Mth.clamp(message.index, 0, 2);

            if (player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() != null) {
                    magicPlayer.getMagicElement().skillsList().get(index).execute(player, world);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
