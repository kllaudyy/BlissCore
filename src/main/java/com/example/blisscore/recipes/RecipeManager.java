package com.example.blisscore.recipes;

import com.example.blisscore.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeManager {

    private final Plugin plugin;

    public RecipeManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerRecipes() {
        registerGemFragmentRecipe();
        registerGemUpgraderRecipe();
    }

    private void registerGemFragmentRecipe() {
        // 1. Создаем результат (наш кастомный осколок)
        ItemStack result = ItemManager.createGemFragment();
        result.setAmount(4); // Даем 4 штуки за крафт

        // 2. Создаем рецепт с уникальным ключом
        NamespacedKey key = new NamespacedKey(plugin, "gem_fragment_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        // 3. Рисуем сетку крафта (3x3)
        recipe.shape(
            "DAD",
            "EIE",
            "DAD"
        );

        // 4. Указываем, что означают буквы
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('A', Material.AMETHYST_CLUSTER);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('I', Material.IRON_BLOCK);

        // 5. Добавляем рецепт в игру
        Bukkit.addRecipe(recipe);
    }

    private void registerGemUpgraderRecipe() {
        ItemStack result = ItemManager.createGemUpgrader();
        NamespacedKey key = new NamespacedKey(plugin, "gem_upgrader_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        recipe.shape(
            "BBB",
            "BSB",
            "BBB"
        );

        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('S', Material.NETHER_STAR);

        Bukkit.addRecipe(recipe);
    }
}
