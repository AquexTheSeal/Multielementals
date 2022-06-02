package com.aquextheseal.woe.network;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.keybinds.SkillClientPacket;
import com.aquextheseal.woe.network.keybinds.SkillPacket;
import com.aquextheseal.woe.network.magicdata.SetSkillCooldownClientPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MENetwork {

    public static String NETWORK_VERSION = "0.1.0";
    private static int packetIndex = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(Multielementals.MODID, "network"), () -> NETWORK_VERSION,
                    version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void initializeNetwork() {
        CHANNEL.registerMessage(packetIndex++, SetSkillCooldownClientPacket.class, SetSkillCooldownClientPacket::encode, SetSkillCooldownClientPacket::decode, SetSkillCooldownClientPacket::execute);

        CHANNEL.registerMessage(packetIndex++, SkillPacket.class, SkillPacket::encode, SkillPacket::decode, SkillPacket::execute);
        CHANNEL.registerMessage(packetIndex++, SkillClientPacket.class, SkillClientPacket::encode, SkillClientPacket::decode, SkillClientPacket::execute);
    }

    public static <MSG> void sendPacketToPlayer(ServerPlayer player, MSG packet) {
        if (player.connection != null) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
        }
    }
}
