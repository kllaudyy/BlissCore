package com.example.blisscore.gems.strength;

import com.example.blisscore.core.AbstractGem;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import com.example.blisscore.gems.strength.abilities.ShadowStalkerAbility;
import com.example.blisscore.gems.strength.passives.StrengthPassive;

public class StrengthGem extends AbstractGem {
    public StrengthGem() {
        super("strength", "Strength Gem", Rarity.BASIC, Tier.T1, 10);
        addPassive(new StrengthPassive());
        addAbility(new ShadowStalkerAbility());
    }
}
