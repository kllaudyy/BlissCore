package com.example.blisscore.manager;

import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Ability;
import com.example.blisscore.core.Passive;
import com.example.blisscore.data.DataManager;
import com.example.blisscore.data.PlayerGemData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GemManager {
    private final Map<String, GemDefinition> registry = new HashMap<>();
    private final Map<UUID, PlayerGemData> playerData = new HashMap<>();
    
    // НОВОЕ: Ссылка на наш "архивариус"
    private DataManager dataManager;

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void registerGem(GemDefinition gem) {
        if (registry.containsKey(gem.getId())) throw new IllegalArgumentException("Duplicate gem: " + gem.getId());
        registry.put(gem.getId(), gem);
    }

    public GemDefinition getGem(String id) { return registry.get(id); }
    public Collection<GemDefinition> getAllGems() { return registry.values(); }

    public PlayerGemData getData(Player player) {
        // Если данных в памяти нет — загружаем их с диска через DataManager
        if (!playerData.containsKey(player.getUniqueId())) {
            if (dataManager != null) {
                playerData.put(player.getUniqueId(), dataManager.loadData(player.getUniqueId()));
            } else {
                playerData.put(player.getUniqueId(), new PlayerGemData(player.getUniqueId()));
            }
        }
        return playerData.get(player.getUniqueId());
    }

    public void equipGem(Player player, String gemId) {
        GemDefinition gem = registry.get(gemId);
        if (gem == null) throw new IllegalArgumentException("Unknown gem: " + gemId);
        PlayerGemData data = getData(player);
        if (data.getGemId() != null) unequipGem(player);
        data.setGemId(gemId);
        gem.onEquip(player);
        
        // Сохраняем изменения на диск
        if (dataManager != null) dataManager.saveData(data);
    }

    public void unequipGem(Player player) {
        PlayerGemData data = getData(player);
        if (data.getGemId() == null) return;
        GemDefinition gem = registry.get(data.getGemId());
        if (gem != null) gem.onUnequip(player);
        data.setGemId(null);
        
        if (dataManager != null) dataManager.saveData(data);
    }

    public GemDefinition getEquippedGem(Player player) {
        String id = getData(player).getGemId();
        return id == null ? null : registry.get(id);
    }

    public void tickPassives() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            GemDefinition gem = getEquippedGem(p);
            if (gem == null || getData(p).isRuined()) continue;
            for (Passive passive : gem.getPassives()) {
                passive.onTick(p);
            }
        }
    }

    public boolean isTrusted(Player source, Player target) {
        if (source.equals(target)) return true;
        return getData(source).isTrusted(target.getUniqueId());
    }

    public boolean activateAbility(Player player, Ability ability) {
        PlayerGemData data = getData(player);
        
        if (data.getTier().ordinal() < ability.getRequiredTier().ordinal()) {
            player.sendMessage("§cЭта способность требует улучшения гема до " + ability.getRequiredTier() + "!");
            return false;
        }

        if (data.isBroken()) {
            player.sendMessage("§cГем сломан.");
            return false;
        }
        if (data.isOnCooldown(ability.getId())) {
            long seconds = data.getRemainingCooldown(ability.getId()) / 1000;
            player.sendMessage("§cКулдаун: " + seconds + "с");
            return false;
        }
        if (!ability.activate(player)) return false;
        data.setCooldown(ability.getId(), ability.getCooldownMillis());
        return true;
    }
}
