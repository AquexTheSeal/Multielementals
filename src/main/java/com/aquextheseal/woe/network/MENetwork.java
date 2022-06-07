package com.aquextheseal.woe.network;

import com.aquextheseal.woe.Multielementals;
import com.aquextheseal.woe.network.elementdata.OpenElementMenuPacket;
import com.aquextheseal.woe.network.elementdata.SetSkillLevelPacket;
import com.aquextheseal.woe.network.keybinds.SkillClientPacket;
import com.aquextheseal.woe.network.keybinds.SkillPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
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

        // Client to server packets
        CHANNEL.registerMessage(packetIndex++, SetSkillLevelPacket.class, SetSkillLevelPacket::encode, SetSkillLevelPacket::decode, SetSkillLevelPacket::execute);

        CHANNEL.registerMessage(packetIndex++, OpenElementMenuPacket.class, OpenElementMenuPacket::encode, OpenElementMenuPacket::decode, OpenElementMenuPacket::execute);

        CHANNEL.registerMessage(packetIndex++, SkillPacket.class, SkillPacket::encode, SkillPacket::decode, SkillPacket::execute);

        // Server to Client packets
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CHANNEL.registerMessage(packetIndex++, SkillClientPacket.class, SkillClientPacket::encode, SkillClientPacket::decode, SkillClientPacket::execute);
        }
    }

    public static <MSG> void sendPacketToPlayer(ServerPlayer player, MSG packet) {
        if (player.connection != null) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
        }
    }
}
