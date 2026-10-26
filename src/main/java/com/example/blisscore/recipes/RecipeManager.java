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
        registerRepairKitRecipe(); // НОВОЕ
    }

    private void registerGemFragmentRecipe() {
        ItemStack result = ItemManager.createGemFragment();
        result.setAmount(4);
        NamespacedKey key = new NamespacedKey(plugin, "gem_fragment_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("DAD", "EIE", "DAD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('A', Material.AMETHYST_CLUSTER);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.addRecipe(recipe);
    }

    private void registerGemUpgraderRecipe() {
        ItemStack result = ItemManager.createGemUpgrader();
        NamespacedKey key = new NamespacedKey(plugin, "gem_upgrader_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BBB", "BSB", "BBB");
        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('S', Material.NETHER_STAR);
        Bukkit.addRecipe(recipe);
    }

    // НОВОЕ: Рецепт Ремонтного набора
    private void registerRepairKitRecipe() {
        ItemStack result = ItemManager.createRepairKit();
        NamespacedKey key = new NamespacedKey(plugin, "repair_kit_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        
        // Сетка (как на твоем скрине: призмарин, наковальня, незерит)
        recipe.shape(
            "FAF",
            "NTN",
            "FAF"
        );
        
        recipe.setIngredient('F', Material.PRISMARINE_SHARD);
        recipe.setIngredient('A', Material.ANVIL);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('T', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        
        Bukkit.addRecipe(recipe);
    }
}
