package com.example.blisscore.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerGemData {
    private final UUID playerId;
    private String gemId;
    private int energy = 10;
    private final Map<String, Long> cooldowns = new HashMap<>();

    public PlayerGemData(UUID playerId) { this.playerId = playerId; }

    public UUID getPlayerId() { return playerId; }
    public String getGemId() { return gemId; }
    public void setGemId(String id) { this.gemId = id; }

    public int getEnergy() { return energy; }
    public void setEnergy(int e) { this.energy = Math.max(0, Math.min(10, e)); }
    public void addEnergy(int d) { setEnergy(energy + d); }

    public boolean isBroken() { return energy <= 0; }
    public boolean isRuined() { return energy <= 1; }

    public boolean isOnCooldown(String abilityId) {
        Long ready = cooldowns.get(abilityId);
        return ready != null && ready > System.currentTimeMillis();
    }
    public long getRemainingCooldown(String abilityId) {
        Long ready = cooldowns.get(abilityId);
        return ready == null ? 0 : Math.max(0, ready - System.currentTimeMillis());
    }
    public void setCooldown(String abilityId, long ms) {
        cooldowns.put(abilityId, System.currentTimeMillis() + ms);
    }
}
