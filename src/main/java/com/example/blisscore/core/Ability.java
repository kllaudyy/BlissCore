package com.example.blisscore.core;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Ability {
    String getId();
    String getDisplayName();
    long getCooldownMillis();
    boolean activate(Player player);

    default void tick(Player player) {}
    default void onHit(Player attacker, LivingEntity target) {}
    default void onEnd(Player player) {}
    default boolean isActive(Player player) { return false; }
}
