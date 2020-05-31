package com.nozemi.spigotpluginboiler.events.impl;

import com.nozemi.spigotpluginboiler.Plugin;
import com.nozemi.spigotpluginboiler.events.EventBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/*
    We want to override /help PLUGINNAME so we use our own help text instead.
 */
public class PlayerCommandEventListener extends EventBase {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().equalsIgnoreCase("/help " + Plugin.instance.getName())) {
            event.setCancelled(true);
            Bukkit.dispatchCommand(event.getPlayer(), Plugin.instance.getName());
        }
    }
}
