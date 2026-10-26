package com.example.blisscore.listeners;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
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
        if (gem == null) return;

        // Если у игрока в руке Улучшатель или другой предмет — не мешаем ItemListener
        if (player.getInventory().getItemInMainHand().hasItemMeta() && 
            player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
            // (Грубая проверка, чтобы не конфликтовать с другими плагинами, в будущем улучшим)
        }

        PlayerGemData data = gemManager.getData(player);
        
        // Определяем, зажат ли Shift
        boolean isShift = player.isSneaking();
        Tier targetTier = isShift ? Tier.T2 : Tier.T1;

        // Ищем способность нужного тира
        Ability abilityToUse = null;
        for (Ability ab : gem.getAbilities()) {
            if (ab.getRequiredTier() == targetTier) {
                abilityToUse = ab;
                break;
            }
        }

        if (abilityToUse != null) {
            event.setCancelled(true);
            gemManager.activateAbility(player, abilityToUse);
        } else if (isShift) {
            player.sendMessage("§cУлучшите гем до T2 (используйте Upgrader), чтобы открыть эту способность!");
        }
    }
}
