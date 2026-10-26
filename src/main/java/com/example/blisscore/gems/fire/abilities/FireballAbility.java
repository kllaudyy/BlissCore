package com.example.blisscore.gems.fire.abilities;

import com.example.blisscore.core.Ability;
import com.example.blisscore.core.Tier;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class FireballAbility implements Ability {

    @Override public String getId() { return "fire_fireball"; }
    @Override public String getDisplayName() { return "Fireball"; }
    
    // Из конфига Xoperr: fire-fireball: 35 секунд
    @Override public long getCooldownMillis() { return 35_000L; }

    // Говорим плагину, что эта способность открывается только на T2
    @Override public Tier getRequiredTier() { return Tier.T2; }

    @Override
    public boolean activate(Player player) {
        // Запускаем стандартный огненный шар (как из Гаста, но от игрока)
        player.launchProjectile(Fireball.class);
        player.sendMessage("§6[Fire] §eВы запустили Огненный Шар!");
        return true;
    }
}
