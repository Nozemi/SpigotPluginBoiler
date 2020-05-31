package com.nozemi.spigotpluginboiler.commands;

import com.nozemi.spigotpluginboiler.Initializer;
import com.nozemi.spigotpluginboiler.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CommandsProvider implements Initializer {

    private List<CommandBase> commands = new ArrayList<>();

    public CommandsProvider() {
    }

    public List<CommandBase> getCommands() {
        return commands;
    }

    public Optional<CommandBase> getCommand(String name) {
        return Optional.empty();
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(Plugin.class.getPackageName());
        Set<Class<? extends CommandBase>> commandClasses = reflections.getSubTypesOf(CommandBase.class);
        commandClasses.forEach(commandClass -> {
            try {
                CommandBase instance = commandClass.getConstructor().newInstance();
                instance.register();
                commands.add(instance);
                Bukkit.getConsoleSender().sendMessage("[" + Plugin.instance.getName() + "] Added command: ");
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
