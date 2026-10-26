package com.example.blisscore.data;

import com.example.blisscore.BlissCore;
import com.example.blisscore.core.Tier;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {

    private final BlissCore plugin;
    private final File dataFolder;

    public DataManager(BlissCore plugin) {
        this.plugin = plugin;
        // Создаем папку plugins/BlissCore/playerdata/
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    // ==========================================
    // СОХРАНЕНИЕ (Запись на диск)
    // ==========================================
    public void saveData(PlayerGemData data) {
        File file = new File(dataFolder, data.getPlayerId().toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        config.set("gemId", data.getGemId());
        config.set("energy", data.getEnergy());
        config.set("tier", data.getTier().name());

        // Сохраняем доверенных игроков (превращаем UUID в строки)
        List<String> trustedStrings = new ArrayList<>();
        for (UUID uuid : data.getTrustedPlayers()) {
            trustedStrings.add(uuid.toString());
        }
        config.set("trusted", trustedStrings);

        // Сохраняем кулдауны
        config.set("cooldowns", data.getCooldowns());

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось сохранить данные для " + data.getPlayerId());
            e.printStackTrace();
        }
    }

    // ==========================================
    // ЗАГРУЗКА (Чтение с диска)
    // ==========================================
    public PlayerGemData loadData(UUID playerId) {
        File file = new File(dataFolder, playerId.toString() + ".yml");
        PlayerGemData data = new PlayerGemData(playerId);

        // Если файла нет (новый игрок) — возвращаем пустые данные
        if (!file.exists()) {
            return data;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Загружаем гем
        if (config.contains("gemId")) {
            data.setGemId(config.getString("gemId"));
        }
        
        // Загружаем энергию
        data.setEnergy(config.getInt("energy", 10));

        // Загружаем тир
        if (config.contains("tier")) {
            try {
                data.setTier(Tier.valueOf(config.getString("tier")));
            } catch (IllegalArgumentException ignored) {}
        }

        // Загружаем доверенных игроков
        List<String> trustedStrings = config.getStringList("trusted");
        for (String s : trustedStrings) {
            data.addTrusted(UUID.fromString(s));
        }

        // Загружаем кулдауны
        if (config.getConfigurationSection("cooldowns") != null) {
            for (String key : config.getConfigurationSection("cooldowns").getKeys(false)) {
                long readyTime = config.getLong("cooldowns." + key);
                data.getCooldowns().put(key, readyTime);
            }
        }

        return data;
    }
}
