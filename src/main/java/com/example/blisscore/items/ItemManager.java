package com.example.blisscore.items;

import com.example.blisscore.core.Tier;
import com.example.blisscore.data.PlayerGemData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    
    public static NamespacedKey ITEM_ID_KEY;
    public static NamespacedKey GEM_ID_KEY; 

    public ItemManager(Plugin plugin) {
        ITEM_ID_KEY = new NamespacedKey(plugin, "custom_item_id");
        GEM_ID_KEY = new NamespacedKey(plugin, "gem_id");
    }

    public static ItemStack createGemFragment() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Gem Fragment").color(NamedTextColor.DARK_AQUA));
        meta.lore(List.of(Component.text("A shard of shattered gem energy").color(NamedTextColor.GRAY)));
        meta.getPersistentDataContainer().set(ITEM_ID_KEY, PersistentDataType.STRING, "gem_fragment");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGemUpgrader() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
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

    // НОВОЕ: Создание ремонтного набора (Repair Kit)
    public static ItemStack createRepairKit() {
        ItemStack item = new ItemStack(Material.PAPER); // Заменишь на свою модель
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Repair Kit").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true));
        meta.lore(List.of(
                Component.text("Drop on a beacon to create a pedestal").color(NamedTextColor.GRAY),
                Component.text("Restores energy to nearby gems").color(NamedTextColor.GRAY)
        ));
        meta.getPersistentDataContainer().set(ITEM_ID_KEY, PersistentDataType.STRING, "repair_kit");
        item.setItemMeta(meta);
        return item;
    }

    // НОВОЕ: Поиск предмета гема в инвентаре игрока
    public static ItemStack findGemItem(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta()) {
                if (item.getItemMeta().getPersistentDataContainer().has(GEM_ID_KEY, PersistentDataType.STRING)) {
                    return item;
                }
            }
        }
        return null;
    }

    public static ItemStack createGemItem(String gemId, PlayerGemData data) {
        ItemStack item = new ItemStack(Material.NAUTILUS_SHELL); 
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = new ArrayList<>();
        
        NamedTextColor gemColor = switch (gemId.toLowerCase()) {
            case "fire" -> NamedTextColor.GOLD;
            case "flux" -> NamedTextColor.AQUA;
            case "puff" -> NamedTextColor.WHITE;
            case "astra" -> NamedTextColor.DARK_PURPLE;
            case "life" -> NamedTextColor.LIGHT_PURPLE;
            case "strength" -> NamedTextColor.DARK_RED;
            case "speed" -> NamedTextColor.YELLOW;
            case "wealth" -> NamedTextColor.DARK_GREEN;
            default -> NamedTextColor.GRAY;
        };
        
        meta.displayName(Component.text(gemId.toUpperCase() + " GEM")
                .color(gemColor)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, false));

        String state = data.isBroken() ? "Broken" : (data.isRuined() ? "Cracked" : "Pristine");
        NamedTextColor stateColor = data.isBroken() ? NamedTextColor.RED : (data.isRuined() ? NamedTextColor.GOLD : NamedTextColor.AQUA);
        
        lore.add(Component.text("(" + state + ")").color(stateColor).decoration(TextDecoration.ITALIC, false));
        
        String slogan = switch (gemId.toLowerCase()) {
            case "fire" -> "MANIPULATE FIRE";
            case "flux" -> "EVERYTHING IS A FLUCTUATION";
            case "puff" -> "BE THE BIGGEST BIRD";
            case "astra" -> "MANAGE THE TIDES OF THE COSMOS";
            case "life" -> "CONTROLS THE BALANCE OF LIFE";
            case "strength" -> "HAVE A STRENGTH OF A ARMY";
            case "speed" -> "WATCH THE WORLD TURN INTO A BLUR";
            case "wealth" -> "FUEL A EMPIRE";
            default -> "";
        };
        lore.add(Component.text(slogan).color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        lore.add(Component.text("⚡ PASSIVES").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        List<String> passives = switch (gemId.toLowerCase()) {
            case "fire" -> List.of("Fire Resistance", "Autosmelt", "Flamestrike", "Flameshot");
            case "flux" -> List.of("Flow State", "Shocking Chance", "Tireless", "Conduction", "Charged");
            case "puff" -> List.of("Fall Damage Immunity", "Enchants Power", "Enchants Punch", "Sculk Silence", "Crop Tramp-Less");
            case "astra" -> List.of("Phasing", "Soul Healing", "Soul Capture");
            case "life" -> List.of("Green Thumb", "Radiant Fist", "Bonus Saturation", "Bonus Absorption", "Enchants Unbreaking", "Wither Immune");
            case "strength" -> List.of("Strength", "Enchant Sharpness");
            case "speed" -> List.of("Speed", "Dolphins Grace", "Enchant Efficiency", "Enchant Soul Speed");
            case "wealth" -> List.of("Hero of the Village", "Luck", "Enchants Mending", "Enchants Fortune", "Enchants Looting", "Bonus Ores", "Extra EXP", "Durability Chip", "Double Debris");
            default -> List.of("Unknown");
        };
        for (String p : passives) {
            lore.add(Component.text("- " + p).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        }
        lore.add(Component.empty());

        lore.add(Component.text("⚡ ABILITY").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        String t1Ability = switch (gemId.toLowerCase()) {
            case "fire" -> "Crisp";
            case "flux" -> "Static Burst";
            case "puff" -> "Double Jump";
            case "astra" -> "Dimensional Drift";
            case "life" -> "Vitality Vortex";
            case "strength" -> "Shadow Stalker";
            case "speed" -> "Thunder Step";
            case "wealth" -> "Pockets";
            default -> "None";
        };
        lore.add(Component.text("- " + t1Ability).color(gemColor).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());

        lore.add(Component.text("✨ POWERS").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));
        
        if (data.getTier() == Tier.T1) {
            lore.add(Component.text("Unknown").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.BOLD, true));
        } else {
            List<String> powers = switch (gemId.toLowerCase()) {
                case "fire" -> List.of("Fireball", "Meteor Shower", "Cozy Campfire");
                case "flux" -> List.of("Energy Beam", "Ground", "Kinetic Overdrive");
                case "puff" -> List.of("Breezy Bash", "Dash");
                case "astra" -> List.of("Daggers", "Unbounded", "Astral Projection", "Astral Void");
                case "life" -> List.of("Heartdrainer", "Heartlock", "Circle of Life");
                case "strength" -> List.of("Frailer", "Nullify", "Chad Strength");
                case "speed" -> List.of("Blur", "Speedy Storm");
                case "wealth" -> List.of("Unfortunate", "Item Lock", "Rich Rush", "Amplification");
                default -> List.of("Unknown");
            };
            for (String power : powers) {
                lore.add(Component.text("- ❖ " + power).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            }
        }

        meta.lore(lore);
        meta.getPersistentDataContainer().set(GEM_ID_KEY, PersistentDataType.STRING, gemId);
        item.setItemMeta(meta);
        return item;
    }
}
