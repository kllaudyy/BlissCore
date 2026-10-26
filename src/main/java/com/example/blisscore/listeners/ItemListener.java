package com.example.blisscore.listeners;

import com.example.blisscore.BlissCore;
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

    private final BlissCore plugin;
    private final GemManager gemManager;

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

        // ==========================================
        // ЛОГИКА УЛУЧШАТЕЛЯ (GEM UPGRADER)
        // ==========================================
        if (customId.equals("gem_upgrader")) {
            event.setCancelled(true);

            PlayerGemData data = gemManager.getData(player);
            if (data.getGemId() == null) {
                player.sendMessage("§cУ вас нет активного гема!");
                return;
            }

            if (data.getTier() == Tier.T2) {
                player.sendMessage("§cВаш гем уже максимального уровня (T2)!");
                return;
            }

            // 1. Меняем тир в памяти
            data.setTier(Tier.T2);
            
            // 2. Находим старый предмет гема и удаляем его
            ItemStack oldGemItem = ItemManager.findGemItem(player);
            if (oldGemItem != null) {
                player.getInventory().remove(oldGemItem);
            }

            // 3. Создаем новый предмет с Т2 и кладем в инвентарь
            ItemStack newGemItem = ItemManager.createGemItem(data.getGemId(), data);
            player.getInventory().addItem(newGemItem);

            // 4. Сохраняем на диск
            plugin.getDataManager().saveData(data);
            
            player.sendMessage("§a§lУСПЕХ! §aВаш гем улучшен до Тира 2!");
            player.sendMessage("§eИспользуйте §6Shift + ПКМ §eчтобы активировать новую способность!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
            
            item.setAmount(item.getAmount() - 1); // Забираем улучшатель
        }

        // ==========================================
        // ЛОГИКА РЕМОНТА (REPAIR KIT)
        // ==========================================
        else if (customId.equals("repair_kit")) {
            // Пока просто заглушка: чиним гем по ПКМ
            event.setCancelled(true);
            PlayerGemData data = gemManager.getData(player);
            
            if (data.getGemId() == null) {
                player.sendMessage("§cУ вас нет гема для ремонта!");
                return;
            }

            if (!data.isBroken() && !data.isRuined()) {
                player.sendMessage("§cВаш гем в порядке, ремонт не требуется!");
                return;
            }

            // Восстанавливаем энергию до 10
            data.setEnergy(10);
            
            // Обновляем предмет в инвентаре (чтобы стерлась надпись "Broken" и вернулась "Pristine")
            ItemStack oldGemItem = ItemManager.findGemItem(player);
            if (oldGemItem != null) {
                player.getInventory().remove(oldGemItem);
            }
            player.getInventory().addItem(ItemManager.createGemItem(data.getGemId(), data));

            plugin.getDataManager().saveData(data);
            
            player.sendMessage("§d§lГЕМ ВОССТАНОВЛЕН! §dЭнергия: 10/10");
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
            
            item.setAmount(item.getAmount() - 1); // Забираем набор
        }
    }
}
