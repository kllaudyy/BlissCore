package com.example.blisscore.items;

import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    
    public static NamespacedKey ITEM_ID_KEY;
    public static NamespacedKey GEM_ID_KEY; // Бирка для гемов

    public ItemManager(Plugin plugin) {
        ITEM_ID_KEY = new NamespacedKey(plugin, "custom_item_id");
        GEM_ID_KEY = new NamespacedKey(plugin, "gem_id");
    }

    // ... (методы createGemFragment и createGemUpgrader оставляем как были) ...

    /**
     * Создает предмет-гем с красивым описанием (как на скрине)
     */
    public static ItemStack createGemItem(String gemId, PlayerGemData data) {
        // Пока используем Emerald, потом заменишь на свою текстуру
        ItemStack item = new ItemStack(Material.EMERALD); 
        ItemMeta meta = item.getItemMeta();

        // Делаем имя жирным и зеленым (в оригинале это кастомный шрифт, мы пока юзаем обычный)
        meta.displayName(Component.text(gemId.toUpperCase() + " GEM")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, false)); // Убираем курсив

        List<Component> lore = new ArrayList<>();
        
        // Состояние (Pristine / Broken)
        String state = data.isBroken() ? "Broken" : (data.isRuined() ? "Cracked" : "Pristine");
        NamedTextColor stateColor = data.isBroken() ? NamedTextColor.RED : (data.isRuined() ? NamedTextColor.GOLD : NamedTextColor.AQUA);
        
        lore.add(Component.text("(" + state + ")").color(stateColor).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("FUEL A EMPIRE").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        // --- PASSIVES ---
        lore.add(Component.text("🛡 PASSIVES").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        lore.add(Component.text("- Hero of the Village").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Luck").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Enchants Mending").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Enchants Fortune").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Enchants Looting").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Bonus Ores").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Extra EXP").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Durability Chip").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("- Double Debris").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        // --- ABILITY ---
        lore.add(Component.text("⚡ ABILITY").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        lore.add(Component.text("- Pockets").color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        // --- POWERS (Зависит от Тира) ---
        lore.add(Component.text("✨ POWERS").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        
        if (data.getTier() == Tier.T1) {
            lore.add(Component.text("Unknown").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.BOLD, true));
        } else {
            // Если T2 — показываем T2 способности (как на скрине)
            lore.add(Component.text("- ❖ Unfortunate").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("- ❖ Item Lock").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());
            lore.add(Component.text("- ✦ Rich Rush").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("- ✦ Amplification").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        }

        meta.lore(lore);
        
        // Ставим невидимую бирку, чтобы знать, что это гем
        meta.getPersistentDataContainer().set(GEM_ID_KEY, PersistentDataType.STRING, gemId);
        
        item.setItemMeta(meta);
        return item;
    }
}
