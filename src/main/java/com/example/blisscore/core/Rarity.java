package com.example.blisscore.core;

public enum Rarity {
    BASIC, MYTHIC, CELESTIAL, LEGENDARY, CHROMATIC;
    public boolean isHigherThan(Rarity o) { return ordinal() > o.ordinal(); }
}
