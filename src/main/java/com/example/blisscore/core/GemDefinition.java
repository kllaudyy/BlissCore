package com.example.blisscore.core;

import org.bukkit.entity.Player;
import java.util.List;

public interface GemDefinition {
    String getId();
    String getDisplayName();
    Rarity getRarity();
    Tier getTier();
    boolean canHaveTier(Tier tier);
    int getEnergyCap();
    List<Passive> getPassives();
    List<Ability> getAbilities();
    List<CombatHook> getCombatHooks();
    default void onEquip(Player p) {}
    default void onUnequip(Player p) {}
}
