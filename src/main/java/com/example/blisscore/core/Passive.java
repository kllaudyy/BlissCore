package com.example.blisscore.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.example.blisscore.manager.GemManager;

public interface Passive {
    default void onTick(Player player) {}
    default void register(Plugin plugin, GemManager gemManager) {}
}
