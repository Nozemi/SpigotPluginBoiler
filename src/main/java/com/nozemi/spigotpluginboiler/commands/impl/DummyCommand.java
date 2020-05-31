package com.nozemi.spigotpluginboiler.commands.impl;

import com.nozemi.spigotpluginboiler.builders.MessageBuilder;
import com.nozemi.spigotpluginboiler.commands.CommandBase;
import com.nozemi.spigotpluginboiler.commands.annotations.Permission;
import com.nozemi.spigotpluginboiler.commands.annotations.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@PluginCommand(
    name = "dummy",
    description = "This is a dummy command",
    aliases = {"dum", "d"}
)
@Permission(
    name = "plugin.dummy",
    message = "You don't have permissions to use this command."
)
public class DummyCommand extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        new MessageBuilder("&9You executed the dummy command.")
            .setSender(sender)
            .setCustomPrefix("&2DummyCommand")
            .send();
        return true;
    }
}
