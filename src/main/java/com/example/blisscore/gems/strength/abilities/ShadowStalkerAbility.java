package com.example.blisscore.gems.strength.abilities;

import com.example.blisscore.core.Ability;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShadowStalkerAbility implements Ability {
    @Override public String getId() { return "strength_stalker"; }
    @Override public String getDisplayName() { return "Shadow Stalker"; }
    @Override public long getCooldownMillis() { return 15000L; } // 15 секунд

    @Override
    public boolean activate(Player player) {
        // Даем невидимость и скорость на 5 секунд
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, false, false, true));
        
        player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation(), 30, 0.5, 0.5, 0.5, 0.05);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.5f);
        player.sendMessage("§8[Strength] §7Вы растворились в тенях.");
        return true;
    }
}
