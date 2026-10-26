package com.example.blisscore.gems.speed.passives;

import com.example.blisscore.core.Passive;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPassive implements Passive {
    @Override
    public void onTick(Player player) {
        // Даем Скорость I (уровень 0) и Дельфиний грацию (для плавания)
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 40, 0, true, false, false));
    }
}
