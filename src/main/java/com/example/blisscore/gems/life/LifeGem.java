package com.example.blisscore.gems.life;

import com.example.blisscore.core.AbstractGem;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import com.example.blisscore.gems.life.abilities.VitalityVortexAbility;
import com.example.blisscore.gems.life.passives.LifePassive;

public class LifeGem extends AbstractGem {
    public LifeGem() {
        super("life", "Life Gem", Rarity.BASIC, Tier.T1, 10);
        addPassive(new LifePassive());
        addAbility(new VitalityVortexAbility());
    }
}
