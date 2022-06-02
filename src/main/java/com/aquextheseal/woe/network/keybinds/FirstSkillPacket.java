package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FirstSkillPacket {
    public int key;

    public FirstSkillPacket() {
    }

    public FirstSkillPacket(int key) {
        this.key = key;
    }

    public static void encode(FirstSkillPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.key);
    }

    public static FirstSkillPacket decode(FriendlyByteBuf buffer) {
        return new FirstSkillPacket(buffer.readInt());
    }

    public static void execute(FirstSkillPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();

            if (player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() != null) {
                    magicPlayer.getMagicElement().getFirstSkill().execute(world.getPlayerByUUID(player.getUUID()), world);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
