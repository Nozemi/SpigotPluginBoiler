package com.nozemi.spigotpluginboiler.commands.impl;

import com.nozemi.spigotpluginboiler.Initializer;
import com.nozemi.spigotpluginboiler.Plugin;
import com.nozemi.spigotpluginboiler.builders.MessageBuilder;
import com.nozemi.spigotpluginboiler.commands.CommandBase;
import com.nozemi.spigotpluginboiler.commands.CommandsProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor, Initializer {

    private final Plugin plugin;

    public HelpCommand() {
        plugin = Plugin.instance;
        PluginCommand command = plugin.getServer().getPluginCommand(plugin.getName().toLowerCase());
        if(command != null) {
            command.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandsProvider commandsProvider = plugin.getInitializer(CommandsProvider.class).orElse(null);

        if(commandsProvider == null) {
            new MessageBuilder("&cSomething went wrong while fetching the plugin's commands.")
                    .setSender(sender)
                    .send();
            return true;
        }

        if(commandsProvider.getCommands()
                .stream()
                .filter(command -> sender.hasPermission(command.getPermission()))
                .count() < 1
        ) {
            new MessageBuilder("&6There are no commands for this plugin.")
                    .setSender(sender)
                    .send();
            return true;
        }

        new MessageBuilder("&aHere is a list of all the available commands:")
                .setSender(sender)
                .send();

        commandsProvider.getCommands()
                .stream()
                .filter(command -> sender.hasPermission(command.getPermission()))
                .forEach(command -> sendCommandHelpLine(command, sender));

        return true;
    }

    private void sendCommandHelpLine(CommandBase command, CommandSender sender) {
        if(command.getUsage().length() > 0) {
            new MessageBuilder(command.getUsage())
                    .setSender(sender)
                    .noPrefix()
                    .send();
        } else {
            new MessageBuilder("/&9" + command.getName() + "&f - " + command.getDescription())
                    .setSender(sender)
                    .noPrefix()
                    .send();
        }
    }
}
