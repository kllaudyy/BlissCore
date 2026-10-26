package com.example.blisscore.tasks;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.GemDefinition;
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

            Component gemName = Component.text(gem.getDisplayName())
                    .color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true);
            Component energyText = Component.text(" | Energy: " + data.getEnergy() + "/10")
                    .color(data.isBroken() ? NamedTextColor.RED : NamedTextColor.GREEN);

            Component cooldownText = Component.empty();
            if (!gem.getAbilities().isEmpty()) {
                Ability primary = gem.getAbilities().get(0);
                long cd = data.getRemainingCooldown(primary.getId());
                if (cd > 0) {
                    long seconds = (cd / 1000) + 1;
                    cooldownText = Component.text(" | " + primary.getDisplayName() + ": " + seconds + "s")
                            .color(NamedTextColor.RED);
                } else {
                    cooldownText = Component.text(" | " + primary.getDisplayName() + ": Ready")
                            .color(NamedTextColor.GRAY);
                }
            }
            player.sendActionBar(Component.empty().append(gemName).append(energyText).append(cooldownText));
        }
    }
}
