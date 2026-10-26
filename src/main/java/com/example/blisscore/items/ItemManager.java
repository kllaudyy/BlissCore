package com.example.blisscore.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ItemManager {
    
    // Ключи для PDC (невидимые бирки на предметах)
    public static NamespacedKey ITEM_ID_KEY;

    public ItemManager(Plugin plugin) {
        // Инициализируем ключ (создаем "бирку" с названием "custom_item_id")
        ITEM_ID_KEY = new NamespacedKey(plugin, "custom_item_id");
    }

    /**
     * Создает Осколок Гема (Gem Fragment)
     */
    public static ItemStack createGemFragment() {
        ItemStack item = new ItemStack(Material.PAPER); // Пока используем бумагу, потом заменим на твою модель
        ItemMeta meta = item.getItemMeta();
        
        // Ставим имя
        meta.displayName(Component.text("Gem Fragment").color(NamedTextColor.DARK_AQUA));
        // Ставим описание (Lore)
        meta.lore(List.of(Component.text("A shard of shattered gem energy").color(NamedTextColor.GRAY)));
        
        // Ставим невидимую бирку! Это очень важно для рецептов.
        meta.getPersistentDataContainer().set(ITEM_ID_KEY, PersistentDataType.STRING, "gem_fragment");
        
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Создает Улучшатель (Gem Upgrader)
     */
    public static ItemStack createGemUpgrader() {
        ItemStack item = new ItemStack(Material.NETHER_STAR); // Звезда Незера
        ItemMeta meta = item.getItemMeta();
        
        meta.displayName(Component.text("Gem Upgrader").color(NamedTextColor.GOLD));
        meta.lore(List.of(
                Component.text("Right Click to upgrade any Tier 1 gem to Tier 2").color(NamedTextColor.GRAY),
                Component.empty(),
                Component.text("Works for ALL gem types:").color(NamedTextColor.DARK_GRAY),
                Component.text("Astra • Fire • Flux • Life • Puff • Speed • Strength • Wealth").color(NamedTextColor.YELLOW)
        ));
        
        meta.getPersistentDataContainer().set(ITEM_ID_KEY, PersistentDataType.STRING, "gem_upgrader");
        
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Проверяет, является ли предмет кастомным (по бирке)
     */
    public static String getCustomItemId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer().get(ITEM_ID_KEY, PersistentDataType.STRING);
    }
}
