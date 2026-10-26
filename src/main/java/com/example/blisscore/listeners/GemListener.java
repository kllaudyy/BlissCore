package com.example.blisscore.listeners;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.manager.GemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class GemListener implements Listener {
    private final GemManager gemManager;
    public GemListener(GemManager gemManager) { this.gemManager = gemManager; }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getAction().isRightClick()) return;

        Player player = event.getPlayer();
        GemDefinition gem = gemManager.getEquippedGem(player);
        if (gem == null || gem.getAbilities().isEmpty()) return;

        Ability ability = gem.getAbilities().get(0);
        event.setCancelled(true);
        gemManager.activateAbility(player, ability);
    }
}
