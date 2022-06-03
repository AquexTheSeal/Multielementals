package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.util.MEMechanicUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillClientPacket {

    public int index;
    public String elementReg;

    public SkillClientPacket(int index, String elementRegistryName) {
        this.index = index;
        this.elementReg = elementRegistryName;
    }

    public static void encode(SkillClientPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeUtf(message.elementReg);
    }

    public static SkillClientPacket decode(FriendlyByteBuf buffer) {
        return new SkillClientPacket(buffer.readInt(), buffer.readUtf(32767));
    }

    public static void execute(SkillClientPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            ClientLevel world = Minecraft.getInstance().level;
            int index = Mth.clamp(message.index, 0, 2);

            MEMechanicUtil.getMagicElementWithString(message.elementReg).skillsList().get(index).onExecution(player, world);
        });
        context.setPacketHandled(true);
    }
}
