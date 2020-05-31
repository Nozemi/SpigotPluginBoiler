package com.nozemi.spigotpluginboiler.events;

import com.nozemi.spigotpluginboiler.Plugin;
import org.bukkit.event.Listener;

public abstract class EventBase implements Listener {

    public EventBase() {
        Plugin.instance.getServer().getPluginManager().registerEvents(this, Plugin.instance);
    }
}
