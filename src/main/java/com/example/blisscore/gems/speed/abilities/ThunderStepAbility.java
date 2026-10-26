package com.example.blisscore.gems.speed.abilities;

import com.example.blisscore.core.Ability;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ThunderStepAbility implements Ability {
    @Override public String getId() { return "speed_thunderstep"; }
    @Override public String getDisplayName() { return "Thunder Step"; }
    @Override public long getCooldownMillis() { return 5000L; } // 5 секунд

    @Override
    public boolean activate(Player player) {
        // Рывок вперед по направлению взгляда
        Location loc = player.getLocation();
        Vector direction = loc.getDirection().normalize().multiply(1.5).setY(0.3);
        
        player.setVelocity(direction);
        
        // Визуал и звук
        player.getWorld().spawnParticle(Particle.CLOUD, loc, 20, 0.5, 0.5, 0.5, 0.1);
        player.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5f, 1.5f);
        
        return true;
    }
}
