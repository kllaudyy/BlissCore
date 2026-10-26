package com.example.blisscore.listeners;

import com.example.blisscore.items.ItemManager;
import com.example.blisscore.manager.GemManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    private final GemManager gemManager;

    public ItemListener(GemManager gemManager) {
        this.gemManager = gemManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        String customId = ItemManager.getCustomItemId(item);
        if (customId == null) return;

        // Если игрок кликнул Улучшателем
        if (customId.equals("gem_upgrader")) {
            event.setCancelled(true); // Отменяем стандартное действие

            // Проверяем, есть ли у него гем
            if (gemManager.getEquippedGem(player) == null) {
                player.sendMessage("§cУ вас нет активного гема!");
                return;
            }

            // ЗАГЛУШКА: Тут мы позже напишем логику улучшения.
            // Пока просто сообщение.
            player.sendMessage("§aУлучшатель использован! (Логика T2 скоро появится)");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
            
            // Удаляем улучшатель из руки (расходуем)
            item.setAmount(item.getAmount() - 1);
        }
    }
}
