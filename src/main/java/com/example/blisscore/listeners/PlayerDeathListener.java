package com.example.blisscore.listeners;

import com.example.blisscore.BlissCore;
import com.example.blisscore.data.PlayerGemData;
import com.example.blisscore.manager.GemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final BlissCore plugin;
    private final GemManager gemManager;

    public PlayerDeathListener(BlissCore plugin) {
        this.plugin = plugin;
        this.gemManager = plugin.getGemManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // 1. Забираем энергию у жертвы (если у нее есть гем)
        if (gemManager.getData(victim).getGemId() != null) {
            PlayerGemData victimData = gemManager.getData(victim);
            victimData.addEnergy(-1); // -1 энергия (из конфига Xoperr)
            plugin.getDataManager().saveData(victimData);

            // Сообщаем жертве
            if (victimData.isBroken()) {
                victim.sendMessage("§c§lВАШ ГЕМ СЛОМАН! §cЭнергия упала до 0. Способности недоступны.");
            } else if (victimData.isRuined()) {
                victim.sendMessage("§c§lВАШ ГЕМ РАЗРУШЕН! §cПассивки отключены. Энергия: " + victimData.getEnergy() + "/10");
            } else {
                victim.sendMessage("§cВы потеряли энергию. Осталось: " + victimData.getEnergy() + "/10");
            }
        }

        // 2. Даем энергию убийце (если у него есть гем)
        if (killer != null && gemManager.getData(killer).getGemId() != null) {
            PlayerGemData killerData = gemManager.getData(killer);
            killerData.addEnergy(1); // +1 энергия
            plugin.getDataManager().saveData(killerData);

            killer.sendMessage("§aВы убили " + victim.getName() + " и получили +1 энергию! (" + killerData.getEnergy() + "/10)");
        }
    }
}
