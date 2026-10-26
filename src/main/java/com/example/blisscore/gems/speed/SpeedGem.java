package com.example.blisscore.gems.speed;

import com.example.blisscore.core.AbstractGem;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import com.example.blisscore.gems.speed.abilities.ThunderStepAbility;
import com.example.blisscore.gems.speed.passives.SpeedPassive;

public class SpeedGem extends AbstractGem {
    public SpeedGem() {
        super("speed", "Speed Gem", Rarity.BASIC, Tier.T1, 10);
        addPassive(new SpeedPassive());
        addAbility(new ThunderStepAbility());
    }
}
