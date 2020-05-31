package com.nozemi.spigotpluginboiler;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Plugin extends JavaPlugin {
    public static Plugin instance;

    private List<Initializer> initializers = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        processInitializers();
    }

    public void processInitializers() {
        Reflections reflections = new Reflections(Plugin.class.getPackageName());
        reflections.getSubTypesOf(Initializer.class).forEach(initializerClass -> {
            try {
                Initializer initializer = initializerClass.getConstructor().newInstance();
                initializer.initialize();
                initializers.add(initializer);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public <T extends Initializer> Optional<T> getInitializer(Class<T> type) {
        return initializers.stream()
                .filter(initializer -> initializer.getClass().getName().equalsIgnoreCase(type.getName()))
                .findFirst()
                .map(type::cast);
    }
}
