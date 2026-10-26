package com.example.blisscore.tasks;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
import com.example.blisscore.manager.GemManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

            if (data.isBroken()) {
                player.sendActionBar(Component.text("BROKEN | 0/10 Energy").color(NamedTextColor.DARK_RED));
                continue;
            }

            // Собираем текст в стиле твоего скрина
            Component message = Component.empty();

            // Добавляем статус каждой способности
            for (Ability ab : gem.getAbilities()) {
                if (ab.getRequiredTier() != data.getTier()) continue;

                long cd = data.getRemainingCooldown(ab.getId());
                
                if (cd > 0) {
                    // Кулдаун (желтый/оранжевый)
                    long seconds = (cd / 1000) + 1;
                    message = message.append(Component.text(seconds + "s ").color(NamedTextColor.YELLOW));
                } else {
                    // Готово (зеленый)
                    message = message.append(Component.text("Ready! ").color(NamedTextColor.GREEN));
                }
                
                message = message.append(Component.text(ab.getDisplayName() + " ").color(NamedTextColor.GRAY));
            }

            // Добавляем энергетику справа
            message = message.append(Component.text(" | 🔥 " + data.getEnergy() + "/10")
                    .color(data.isRuined() ? NamedTextColor.RED : NamedTextColor.AQUA));

            player.sendActionBar(message);
        }
    }
}
