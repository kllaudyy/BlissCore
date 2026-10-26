package com.example.blisscore;

import com.example.blisscore.commands.BlissCommand;
import com.example.blisscore.data.DataManager;
import com.example.blisscore.gems.fire.FireGem;
import com.example.blisscore.items.ItemManager;
import com.example.blisscore.listeners.CombatListener;
import com.example.blisscore.listeners.GemListener;
import com.example.blisscore.listeners.ItemListener;
import com.example.blisscore.listeners.PlayerJoinListener;
import com.example.blisscore.manager.GemManager;
import com.example.blisscore.recipes.RecipeManager;
import com.example.blisscore.tasks.ActionBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlissCore extends JavaPlugin {

    private GemManager gemManager;
    private DataManager dataManager; // НОВОЕ

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        // 1. Инициализируем "архивариус"
        dataManager = new DataManager(this);
        
        // 2. Создаем менеджер и связываем его с архивариусом
        gemManager = new GemManager();
        gemManager.setDataManager(dataManager);

        new ItemManager(this);
        gemManager.registerGem(new FireGem());

        gemManager.getAllGems().forEach(gem -> 
            gem.getPassives().forEach(p -> p.register(this, gemManager))
        );

        new RecipeManager(this).registerRecipes();

        getServer().getPluginManager().registerEvents(new GemListener(gemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(gemManager), this);
        getServer().getPluginManager().registerEvents(new CombatListener(gemManager), this);

        getServer().getScheduler().runTaskTimer(this, () -> gemManager.tickPassives(), 20L, 20L);
        getServer().getScheduler().runTaskTimer(this, new ActionBarTask(gemManager), 0L, 10L);

        getCommand("bliss").setExecutor(new BlissCommand(this));

        getLogger().info("BlissCore запущен! Система сохранений активна.");
    }

    @Override
    public void onDisable() {
        // При выключении сервера сохраняем всё, что успели накопить (опционально)
        getLogger().info("BlissCore выключен.");
    }

    public GemManager getGemManager() {
        return gemManager;
    }
}
