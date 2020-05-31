package com.nozemi.spigotpluginboiler.commands;

import com.nozemi.spigotpluginboiler.Plugin;
import com.nozemi.spigotpluginboiler.builders.MessageBuilder;
import com.nozemi.spigotpluginboiler.commands.annotations.Permission;
import com.nozemi.spigotpluginboiler.commands.annotations.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public abstract class CommandBase implements CommandExecutor {
    private String name;
    private String description;
    private String[] aliases;
    private String usage;

    private String permission;
    private String permissionMessage;

    public CommandBase() {
        processAnnotations();
    }

    public void processAnnotations() {
        if(getClass().isAnnotationPresent(PluginCommand.class)) {
            PluginCommand annotation = getClass().getAnnotation(PluginCommand.class);
            this.name = annotation.name();
            this.description = annotation.description();
            this.aliases = annotation.aliases();
            this.usage = annotation.usage();
        }

        if(getClass().isAnnotationPresent(Permission.class)) {
            Permission annotation = getClass().getAnnotation(Permission.class);
            this.permission = annotation.name();
            this.permissionMessage = annotation.message();
        }
    }

    public void register() {

        getCommandMap().ifPresent(commandMap -> commandMap.register(Plugin.instance.getName(), new Command(this.name, this.description, this.usage, Arrays.asList(this.aliases)) {

            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                if(sender.hasPermission(permission)) {
                    return onCommand(sender, this, commandLabel, args);
                } else {
                    new MessageBuilder(permissionMessage)
                        .setSender(sender)
                        .send();
                    return true;
                }
            }
        }));
    }

    private static Optional<CommandMap> getCommandMap() {
        Optional<Class<?>> optCraftServerClass = getOBCClass();

        CommandMap commandMap = null;

        if (optCraftServerClass.isPresent()) {
            try {
                Class<?> craftServerClass = optCraftServerClass.get();
                final Field f = craftServerClass.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getServer());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(commandMap);
    }

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static Optional<Class<?>> getOBCClass() {
        try {
            return Optional.of(Class.forName("org.bukkit.craftbukkit." + version + "." + "CraftServer"));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }
}
