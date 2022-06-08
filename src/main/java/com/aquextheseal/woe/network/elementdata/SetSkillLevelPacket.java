package com.aquextheseal.woe.network.elementdata;

import com.aquextheseal.woe.util.MESystemUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSkillLevelPacket {

    public int index;
    public int value;
    public int xpChange;

    public SetSkillLevelPacket() {
    }

    public SetSkillLevelPacket(int index, int value, int xpChange) {
        this.index = index;
        this.value = value;
        this.xpChange = xpChange;
    }

    public static void encode(SetSkillLevelPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeInt(message.value);
        buffer.writeInt(message.xpChange);
    }

    public static SetSkillLevelPacket decode(FriendlyByteBuf buffer) {
        return new SetSkillLevelPacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static void execute(SetSkillLevelPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();

            if (player instanceof MagicPlayer magicPlayer) {
                MESystemUtil.setSkillLevelOfIndex(message.index, message.value, magicPlayer);
                player.giveExperiencePoints(message.xpChange);

                if (world instanceof ServerLevel server) {
                    server.sendParticles(
                            ParticleTypes.ENCHANT, player.getX(), player.getY() + 0.75D, player.getZ(),
                            40, 0.75F, 0.75F, 0.75F, 0.01
                    );
                }
            }
        });
        context.setPacketHandled(true);
    }
}
