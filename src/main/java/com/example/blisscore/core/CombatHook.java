package com.example.blisscore.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface CombatHook {
    default double onDealDamage(Player attacker, LivingEntity target, double damage) { return damage; }
    default double onTakeDamage(Player victim, Entity damager, double damage) { return damage; }
    default void onKill(Player killer, LivingEntity victim) {}
}
