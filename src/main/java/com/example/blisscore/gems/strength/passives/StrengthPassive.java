package com.example.blisscore.gems.strength.passives;

import com.example.blisscore.core.Passive;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrengthPassive implements Passive {
    @Override
    public void onTick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 40, 0, true, false, false));
    }
}
