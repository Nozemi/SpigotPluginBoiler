package com.nozemi.spigotpluginboiler.builders;

import com.nozemi.spigotpluginboiler.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageBuilder {
    private Player player;
    private String customPrefix = null;
    private boolean noPrefix = false;
    private String message;

    public MessageBuilder(String message) {
        this.message = message;
    }

    public MessageBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public MessageBuilder noPrefix() {
        this.noPrefix = true;
        return this;
    }

    public MessageBuilder setCustomPrefix(String customPrefix) {
        this.customPrefix = customPrefix;
        return this;
    }

    public String build() {
        String result = message;

        if(!noPrefix) {
            result = "&f[&9" + (customPrefix == null ?  Plugin.instance.getName() : customPrefix) + "&f] " + message;
        }

        return result;
    }

    public void send() {
        if(player != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', build()));
        } else {
            Bukkit.getConsoleSender().sendMessage("Couldn't send the message because a player was not defined.");
        }
    }
}
