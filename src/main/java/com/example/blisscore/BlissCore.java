package com.example.blisscore;

import com.example.blisscore.commands.BlissCommand;
import com.example.blisscore.gems.fire.FireGem;
import com.example.blisscore.items.ItemManager;
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

        // 1. Инициализация менеджера предметов (создаем PDC ключи)
        new ItemManager(this);

        // 2. Регистрация гемов
        gemManager.registerGem(new FireGem());

        // 3. Регистрация пассивок
        gemManager.getAllGems().forEach(gem -> 
            gem.getPassives().forEach(p -> p.register(this, gemManager))
        );

        // 4. Регистрация рецептов крафта
        new RecipeManager(this).registerRecipes();

        // 5. Слушатели событий
        getServer().getPluginManager().registerEvents(new GemListener(gemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(gemManager), this); // <-- Новый

        // 6. Тикер пассивок (раз в секунду)
        getServer().getScheduler().runTaskTimer(this, 
            () -> gemManager.tickPassives(), 20L, 20L);

        // 7. Action Bar (дважды в секунду)
        getServer().getScheduler().runTaskTimer(this, 
            new ActionBarTask(gemManager), 0L, 10L);

        // 8. Команды
        getCommand("bliss").setExecutor(new BlissCommand(this));

        getLogger().info("BlissCore успешно запущен! Рецепты и предметы добавлены.");
    }

    public GemManager getGemManager() {
        return gemManager;
    }
}
