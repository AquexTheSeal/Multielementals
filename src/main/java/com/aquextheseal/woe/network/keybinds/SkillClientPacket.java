package com.aquextheseal.woe.network.keybinds;

import com.aquextheseal.woe.magic.skilldata.HoldableMagicSkill;
import com.aquextheseal.woe.magic.skilldata.MagicSkill;
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
    public boolean onRelease;

    public SkillClientPacket(int index, String elementRegistryName) {
        this(index, elementRegistryName, false);
    }

    public SkillClientPacket(int index, String elementRegistryName, boolean onRelease) {
        this.index = index;
        this.elementReg = elementRegistryName;
        this.onRelease = onRelease;
    }

    public static void encode(SkillClientPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.index);
        buffer.writeUtf(message.elementReg);
        buffer.writeBoolean(message.onRelease);
    }

    public static SkillClientPacket decode(FriendlyByteBuf buffer) {
        return new SkillClientPacket(buffer.readInt(), buffer.readUtf(32767), buffer.readBoolean());
    }

    public static void execute(SkillClientPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
                LocalPlayer player = Minecraft.getInstance().player;
                ClientLevel world = Minecraft.getInstance().level;
                int index = Mth.clamp(message.index, 0, 2);
                MagicSkill skill = MEMechanicUtil.getMagicElementWithString(message.elementReg).skillsList().get(index);

                if (!message.onRelease) {
                    skill.onExecution(player, world);
                } else {
                    if (skill instanceof HoldableMagicSkill holdableSkill) {
                        holdableSkill.onRelease(player, world);
                    }
                }
        });
        context.setPacketHandled(true);
    }
}
