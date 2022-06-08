package com.aquextheseal.woe.command;

import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class SetSkillLevelCommand {

    public SetSkillLevelCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        addSlot("first", dispatcher);
        addSlot("second", dispatcher);
        addSlot("third", dispatcher);
        addSlot("all", dispatcher);
    }

    private int setElementLevel(CommandSourceStack source, String slot, int level) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        if (player instanceof MagicPlayer magicPlayer) {
            switch (slot) {
                case "first" -> magicPlayer.setFirstSkillLevel(level);
                case "second" -> magicPlayer.setSecondSkillLevel(level);
                case "third" -> magicPlayer.setThirdSkillLevel(level);
                case "all" -> {
                    magicPlayer.setFirstSkillLevel(level);
                    magicPlayer.setSecondSkillLevel(level);
                    magicPlayer.setThirdSkillLevel(level);
                }
            }
        }
        source.sendSuccess(new TranslatableComponent("Successfully set skill level."), true);
        return 1;
    }

    private int clearElementLevel(CommandSourceStack source, String slot) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        if (player instanceof MagicPlayer magicPlayer) {
            switch (slot) {
                case "first" -> magicPlayer.setFirstSkillLevel(0);
                case "second" -> magicPlayer.setSecondSkillLevel(0);
                case "third" -> magicPlayer.setThirdSkillLevel(0);
                case "all" -> {
                    magicPlayer.setFirstSkillLevel(0);
                    magicPlayer.setSecondSkillLevel(0);
                    magicPlayer.setThirdSkillLevel(0);
                }
            }
        }
        source.sendSuccess(new TranslatableComponent("Successfully cleared skill level."), true);
        return 1;
    }

    public void addSlot(String slot, CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("skillLevel").requires((command) -> command.hasPermission(2))
                .then(Commands.literal("set").then(Commands.literal(slot).then(Commands.argument("amount", IntegerArgumentType.integer())
                .executes((command) -> setElementLevel(command.getSource(), slot, IntegerArgumentType.getInteger(command, "amount"))))))
        );
        dispatcher.register(Commands.literal("skillLevel").requires((command) -> command.hasPermission(2)).then(Commands.literal("clear").then(Commands.literal(slot)
                .executes((command) -> clearElementLevel(command.getSource(), slot))))
        );
    }
}
