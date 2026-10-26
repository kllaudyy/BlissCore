package com.example.blisscore.tasks;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
import com.example.blisscore.manager.GemManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {
    private final GemManager gemManager;

    public ActionBarTask(GemManager gemManager) { this.gemManager = gemManager; }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            GemDefinition gem = gemManager.getEquippedGem(player);
            if (gem == null) continue;
            PlayerGemData data = gemManager.getData(player);

            // НОВОЕ: Добавляем отображение Тира (T1 / T2)
            Component gemName = Component.text(gem.getDisplayName() + " " + data.getTier())
                    .color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true);
            
            Component energyText = Component.text(" | Energy: " + data.getEnergy() + "/10")
                    .color(data.isBroken() ? NamedTextColor.RED : NamedTextColor.GREEN);

            // Показываем кулдаун способности текущего тира
            Tier targetTier = data.getTier();
            Component cooldownText = Component.empty();
            
            for (Ability ab : gem.getAbilities()) {
                if (ab.getRequiredTier() == targetTier) {
                    long cd = data.getRemainingCooldown(ab.getId());
                    if (cd > 0) {
                        long seconds = (cd / 1000) + 1;
                        cooldownText = Component.text(" | " + ab.getDisplayName() + ": " + seconds + "s")
                                .color(NamedTextColor.RED);
                    } else {
                        cooldownText = Component.text(" | " + ab.getDisplayName() + ": Ready")
                                .color(NamedTextColor.GRAY);
                    }
                    break;
                }
            }
            
            player.sendActionBar(Component.empty().append(gemName).append(energyText).append(cooldownText));
        }
    }
}
