package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
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
    public boolean onRelease;

    public SkillPacket(int index, int key) {
        this(index, key, false);
    }

    public SkillPacket(int index, int key, boolean onRelease) {
        this.index = index;
        this.key = key;
        this.onRelease = onRelease;
    }

    public static void encode(SkillPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeInt(message.key);
        buffer.writeBoolean(message.onRelease);
    }

    public static SkillPacket decode(FriendlyByteBuf buffer) {
        return new SkillPacket(buffer.readInt(), buffer.readInt(), buffer.readBoolean());
    }

    public static void execute(SkillPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level world = player.getCommandSenderWorld();
            int index = Mth.clamp(message.index, 0, 2);

            if (player instanceof MagicPlayer magicPlayer) {
                MagicSkill skill = magicPlayer.getMagicElement().skillsList().get(index);

                if (magicPlayer.getMagicElement() != null) {
                    if (!message.onRelease) {
                        skill.execute(player, world);
                    } else {
                        if (skill instanceof HoldableMagicSkill holdableSkill) {
                            holdableSkill.release(player, world);
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
