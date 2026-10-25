package com.example.blisscore.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGem implements GemDefinition {
    private final String id, displayName;
    private final Rarity rarity;
    private final Tier tier;
    private final int energyCap;
    private final List<Passive> passives = new ArrayList<>();
    private final List<Ability> abilities = new ArrayList<>();
    private final List<CombatHook> hooks = new ArrayList<>();

    protected AbstractGem(String id, String displayName, Rarity rarity, Tier tier, int energyCap) {
        this.id = id; this.displayName = displayName;
        this.rarity = rarity; this.tier = tier; this.energyCap = energyCap;
    }

    protected void addPassive(Passive p) { passives.add(p); }
    protected void addAbility(Ability a) { abilities.add(a); }
    protected void addHook(CombatHook h) { hooks.add(h); }

    @Override public String getId() { return id; }
    @Override public String getDisplayName() { return displayName; }
    @Override public Rarity getRarity() { return rarity; }
    @Override public Tier getTier() { return tier; }
    @Override public int getEnergyCap() { return energyCap; }
    @Override public List<Passive> getPassives() { return passives; }
    @Override public List<Ability> getAbilities() { return abilities; }
    @Override public List<CombatHook> getCombatHooks() { return hooks; }

    @Override
    public boolean canHaveTier(Tier t) {
        return switch (rarity) {
            case BASIC -> t == Tier.T1 || t == Tier.T2 || t == Tier.T3;
            case MYTHIC -> t == Tier.T2 || t == Tier.T3;
            case CELESTIAL -> t == Tier.T2 || t == Tier.T3;
            case LEGENDARY -> t == Tier.T3;
            case CHROMATIC -> t == Tier.T4;
        };
    }
}
