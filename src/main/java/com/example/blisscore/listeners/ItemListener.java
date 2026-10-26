package com.example.blisscore.listeners;

import com.example.blisscore.BlissCore; // Импортируем наш главный класс
import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
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

    private final BlissCore plugin; // Храним ссылку на плагин
    private final GemManager gemManager;

    // В конструктор теперь передается BlissCore
    public ItemListener(BlissCore plugin) {
        this.plugin = plugin;
        this.gemManager = plugin.getGemManager();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        String customId = ItemManager.getCustomItemId(item);
        if (customId == null) return;

        if (customId.equals("gem_upgrader")) {
            event.setCancelled(true);

            if (gemManager.getEquippedGem(player) == null) {
                player.sendMessage("§cУ вас нет активного гема!");
                return;
            }

            PlayerGemData data = gemManager.getData(player);

            if (data.getTier() == Tier.T2) {
                player.sendMessage("§cВаш гем уже максимального уровня (T2)!");
                return;
            }

            // Улучшаем!
            data.setTier(Tier.T2);
            
            // НОВОЕ: Сохраняем на диск через DataManager плагина
            plugin.getDataManager().saveData(data);
            
            player.sendMessage("§a§lУСПЕХ! §aВаш гем улучшен до Тира 2!");
            player.sendMessage("§eИспользуйте §6Shift + ПКМ §eчтобы активировать новую способность!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
            
            item.setAmount(item.getAmount() - 1);
        }
    }
}
