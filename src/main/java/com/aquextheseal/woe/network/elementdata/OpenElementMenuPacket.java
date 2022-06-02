package com.aquextheseal.woe.network.elementdata;

import com.aquextheseal.woe.registry.MEContainerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenElementMenuPacket {

    public OpenElementMenuPacket() {
    }

    public static void encode(OpenElementMenuPacket message, FriendlyByteBuf buffer) {
    }

    public static OpenElementMenuPacket decode(FriendlyByteBuf buffer) {
        return new OpenElementMenuPacket();
    }

    public static void execute(OpenElementMenuPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            BlockPos blockPos = new BlockPos(player.getX(), player.getY(), player.getZ());

            NetworkHooks.openGui(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return new TextComponent("");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return MEContainerTypes.ELEMENT_MENU.get().create(id, inventory);
                }
            }, blockPos);

            context.setPacketHandled(true);
        });
    }
}
