package com.nozemi.spigotpluginboiler.events;

import com.nozemi.spigotpluginboiler.Initializer;
import com.nozemi.spigotpluginboiler.Plugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class EventsProvider implements Initializer {

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(Plugin.class.getPackageName());
        Set<Class<? extends EventBase>> eventClasses = reflections.getSubTypesOf(EventBase.class);
        eventClasses.forEach(eventClass -> {
            try {
                eventClass.getConstructor().newInstance();
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
