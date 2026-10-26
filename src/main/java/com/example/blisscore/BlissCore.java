package com.example.blisscore;

import com.example.blisscore.commands.BlissCommand;
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

    @Override
    public void onEnable() {
        saveDefaultConfig();
        gemManager = new GemManager();

        new ItemManager(this);
        gemManager.registerGem(new FireGem());

        gemManager.getAllGems().forEach(gem -> 
            gem.getPassives().forEach(p -> p.register(this, gemManager))
        );

        new RecipeManager(this).registerRecipes();

        // Слушатели
        getServer().getPluginManager().registerEvents(new GemListener(gemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(gemManager), this);
        // НОВОЕ: Регистрируем слушатель боя
        getServer().getPluginManager().registerEvents(new CombatListener(gemManager), this);

        getServer().getScheduler().runTaskTimer(this, () -> gemManager.tickPassives(), 20L, 20L);
        getServer().getScheduler().runTaskTimer(this, new ActionBarTask(gemManager), 0L, 10L);

        getCommand("bliss").setExecutor(new BlissCommand(this));

        getLogger().info("BlissCore успешно запущен! Система доверия активна.");
    }

    public GemManager getGemManager() {
        return gemManager;
    }
}
