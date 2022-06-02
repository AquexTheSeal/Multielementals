package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.util.MagicElementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FirstSkillClientPacket {

    public int index;
    public String elementReg;

    public FirstSkillClientPacket(int index, String elementRegistryName) {
        this.index = index;
        this.elementReg = elementRegistryName;
    }

    public static void encode(FirstSkillClientPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeUtf(message.elementReg);
    }

    public static FirstSkillClientPacket decode(FriendlyByteBuf buffer) {
        return new FirstSkillClientPacket(buffer.readInt(), buffer.readUtf(32767));
    }

    public static void execute(FirstSkillClientPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            ClientLevel world = Minecraft.getInstance().level;
            int index = Mth.clamp(message.index, 0, 2);

            MagicElementUtil.getMagicElementWithString(message.elementReg).skillsList()[index].onExecution(player, world);
        });
        context.setPacketHandled(true);
    }
}
