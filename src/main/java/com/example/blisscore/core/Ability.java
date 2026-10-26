package com.example.blisscore.core;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Ability {
    String getId();
    String getDisplayName();
    long getCooldownMillis();
    boolean activate(Player player);

    // НОВОЕ: Какая способность требует какой тир. По умолчанию T1.
    default Tier getRequiredTier() { return Tier.T1; }

    default void tick(Player player) {}
    default void onHit(Player attacker, LivingEntity target) {}
    default void onEnd(Player player) {}
    default boolean isActive(Player player) { return false; }
}
