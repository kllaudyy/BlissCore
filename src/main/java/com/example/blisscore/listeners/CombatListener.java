package com.example.blisscore.listeners;

import com.example.blisscore.manager.GemManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final GemManager gemManager;

    public CombatListener(GemManager gemManager) {
        this.gemManager = gemManager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // 1. Проверяем, что урон наносится игроку
        if (!(event.getEntity() instanceof Player victim)) return;

        Player attacker = null;

        // 2. Выясняем, КТО нанес урон
        Entity damager = event.getDamager();
        
        // Если бьют рукой или мечом (сам игрок)
        if (damager instanceof Player) {
            attacker = (Player) damager;
        } 
        // Если бьют снарядом (стрела, огненный шар)
        else if (damager instanceof Projectile projectile && projectile.getShooter() instanceof Player) {
            attacker = (Player) projectile.getShooter();
        }

        // Если мы не нашли атакующего игрока (например, скелет) — выходим
        if (attacker == null) return;

        // 3. ГЛАВНАЯ ПРОВЕРКА: Если атакующий доверяет жертве — отменяем урон!
        if (gemManager.isTrusted(attacker, victim)) {
            event.setCancelled(true);
            
            // Если это был огненный шар, тушим его (убираем визуал)
            if (damager instanceof Fireball) {
                damager.remove();
            }
            
            attacker.sendMessage("§7Вы не можете навредить " + victim.getName() + " (Trusted).");
        }
    }
}
