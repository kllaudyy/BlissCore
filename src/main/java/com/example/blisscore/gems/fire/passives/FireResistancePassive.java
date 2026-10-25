package com.example.blisscore.gems.fire.passives;

import com.example.blisscore.core.Passive;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireResistancePassive implements Passive {
    @Override
    public void onTick(Player player) {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.FIRE_RESISTANCE, 40, 0, true, false, false));
    }
}
