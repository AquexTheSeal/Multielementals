package com.aquextheseal.woe.command;

import com.aquextheseal.woe.magic.skilldata.MagicSkill;
import com.aquextheseal.woe.util.MagicElementUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public class ClearElementCommand {

    public ClearElementCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("clearElement").requires((command) -> {
            return command.hasPermission(2);
        }).executes((p_136721_) -> {
            return clearElement(p_136721_.getSource(), Collections.singleton(p_136721_.getSource().getPlayerOrException()));
        }).then(Commands.argument("targets", EntityArgument.players()).executes((p_136719_) -> {
            return clearElement(p_136719_.getSource(), EntityArgument.getPlayers(p_136719_, "targets"));
        })));
    }

    private int clearElement(CommandSourceStack source, Collection<ServerPlayer> pTargets) {
        for(ServerPlayer player : pTargets) {
            if (player instanceof MagicPlayer magicPlayer) {
                if (magicPlayer.getMagicElement() != null) {
                    for (MagicSkill skill : magicPlayer.getMagicElement().skillsList()) {
                        skill.setCooldownCount(player, 0);
                    }
                    magicPlayer.setMagicElement(MagicElementUtil.getMagicElementWithString(MagicPlayer.EMPTY));
                    source.sendSuccess(new TranslatableComponent("Successfully cleared elements"), true);
                }
            }
        }
        return pTargets.size();
    }
}
