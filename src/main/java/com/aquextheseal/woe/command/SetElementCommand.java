package com.aquextheseal.woe.command;

import com.aquextheseal.woe.magic.MagicElement;
import com.aquextheseal.woe.registry.MEMagicElements;
import com.aquextheseal.woe.util.MagicElementUtil;
import com.aquextheseal.woe.util.mixininterfaces.MagicPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.apache.commons.lang3.text.WordUtils;

public class SetElementCommand {

    public SetElementCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        for (MagicElement element : MEMagicElements.MAGIC_ELEMENTS.values()) {
            registerSECommand(element.getElementRegistryName(), dispatcher);
        }
    }

    private int setElement(CommandSourceStack source, String element) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();

        if (player instanceof MagicPlayer magicPlayer) {
            if (magicPlayer.getMagicElement() == null) {
                magicPlayer.setMagicElement(MagicElementUtil.getMagicElementWithString(element));
                source.sendSuccess(new TranslatableComponent("Set Skill to be " + capitalizedSpaced(element)), true);
            } else {
                source.sendFailure(new TranslatableComponent("You cannot change your skill if you already have one!"));
            }
        }
        return 1;
    }

    public void registerSECommand(String name, CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("selectElement").then(Commands.literal(name)
                .executes((command) -> setElement(command.getSource(), name)))
        );
    }

    public String capitalizedSpaced(String string) {
        return WordUtils.capitalize(string.replace("_", " "));
    }
}
