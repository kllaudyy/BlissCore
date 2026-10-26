package com.example.blisscore.gems.fire;

import com.example.blisscore.core.AbstractGem;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import com.example.blisscore.gems.fire.abilities.CrispAbility;
import com.example.blisscore.gems.fire.abilities.FireballAbility; // Импортируем новую способность
import com.example.blisscore.gems.fire.passives.AutoSmeltPassive;
import com.example.blisscore.gems.fire.passives.FireResistancePassive;

public class FireGem extends AbstractGem {
    public FireGem() {
        super("fire", "Fire Gem", Rarity.BASIC, Tier.T1, 10);
        
        addPassive(new FireResistancePassive());
        addPassive(new AutoSmeltPassive());
        
        // Добавляем способности
        addAbility(new CrispAbility());     // Это T1 (по умолчанию)
        addAbility(new FireballAbility());  // Это T2 (мы указали это внутри класса)
    }
}
