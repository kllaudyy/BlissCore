package com.example.blisscore;

import com.example.blisscore.commands.BlissCommand;
import com.example.blisscore.gems.fire.FireGem;
import com.example.blisscore.listeners.GemListener;
import com.example.blisscore.listeners.PlayerJoinListener;
import com.example.blisscore.manager.GemManager;
import com.example.blisscore.tasks.ActionBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlissCore extends JavaPlugin {

    private GemManager gemManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        gemManager = new GemManager();

        // 1. Регистрация гемов
        gemManager.registerGem(new FireGem());

        // 2. Регистрация пассивок (для тех, кто слушает события)
        gemManager.getAllGems().forEach(gem -> 
            gem.getPassives().forEach(p -> p.register(this, gemManager))
        );

        // 3. Слушатели событий
        getServer().getPluginManager().registerEvents(new GemListener(gemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // 4. Тикер пассивок (раз в секунду)
        getServer().getScheduler().runTaskTimer(this, 
            () -> gemManager.tickPassives(), 20L, 20L);

        // 5. Action Bar (дважды в секунду)
        getServer().getScheduler().runTaskTimer(this, 
            new ActionBarTask(gemManager), 0L, 10L);

        // 6. Команды
        getCommand("bliss").setExecutor(new BlissCommand(this));

        getLogger().info("BlissCore успешно запущен!");
    }

    public GemManager getGemManager() {
        return gemManager;
    }
}
