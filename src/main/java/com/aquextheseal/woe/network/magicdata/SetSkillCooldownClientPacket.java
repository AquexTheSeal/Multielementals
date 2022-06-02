package com.aquextheseal.woe.network.magicdata;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSkillCooldownClientPacket {

    public int skillIndex;
    public int cooldown;

    public SetSkillCooldownClientPacket(int skillIndex, int elementRegistryName) {
        this.skillIndex = skillIndex;
        this.cooldown = elementRegistryName;
    }

    public static void encode(SetSkillCooldownClientPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.skillIndex);
        buffer.writeInt(message.cooldown);
    }

    public static SetSkillCooldownClientPacket decode(FriendlyByteBuf buffer) {
        return new SetSkillCooldownClientPacket(buffer.readInt(), buffer.readInt());
    }

    public static void execute(SetSkillCooldownClientPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            int index = Mth.clamp(message.skillIndex, 0, 2);

            if (player instanceof MagicPlayer magicPlayer) {
                MagicElement playerElement = magicPlayer.getMagicElement();
                if (playerElement != null) {
                    playerElement.skillsList().get(index).setCooldownCountForClient(player, message.cooldown);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
