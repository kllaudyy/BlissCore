package com.example.blisscore.listeners;

import com.example.blisscore.BlissCore;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Random;

public class PlayerJoinListener implements Listener {
    private final BlissCore plugin;
    private final Random random = new Random();

    public PlayerJoinListener(BlissCore plugin) { this.plugin = plugin; }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Вызываем getData(), чтобы DataManager загрузил файл с диска
        plugin.getGemManager().getData(player);

        if (!plugin.getConfig().getBoolean("smp.started", false)) return;
        
        if (plugin.getGemManager().getData(player).getGemId() == null) {
            List<GemDefinition> starterGems = plugin.getGemManager().getAllGems().stream()
                    .filter(g -> g.getRarity() == Rarity.BASIC && g.getTier() == Tier.T1)
                    .toList();

            if (!starterGems.isEmpty()) {
                GemDefinition gem = starterGems.get(random.nextInt(starterGems.size()));
                plugin.getGemManager().equipGem(player, gem.getId());
                player.sendMessage("§a§lДобро пожаловать! §aВаш гем: §e" + gem.getDisplayName());
            }
        }
    }

    // НОВОЕ: При выходе сохраняем данные в файл
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Если у игрока были загружены данные — сохраняем их
        if (plugin.getGemManager().getData(player) != null) {
            // (В будущем мы вызовем DataManager.saveData, сейчас он сохраняется при изменениях)
        }
    }
}
