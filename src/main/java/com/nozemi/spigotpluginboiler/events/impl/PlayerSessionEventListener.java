package com.nozemi.spigotpluginboiler.events.impl;

import com.nozemi.spigotpluginboiler.events.EventBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSessionEventListener extends EventBase {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        event.getPlayer().sendMessage("You just joined the server.");
    }
}
