package com.example.blisscore.gems.fire.passives;

import com.example.blisscore.core.Passive;
import com.example.blisscore.manager.GemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class AutoSmeltPassive implements Passive, Listener {
    private static final Map<Material, Material> SMELT_MAP = Map.of(
        Material.IRON_ORE, Material.IRON_INGOT,
        Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT,
        Material.GOLD_ORE, Material.GOLD_INGOT,
        Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT,
        Material.COPPER_ORE, Material.COPPER_INGOT
    );

    private GemManager gemManager;

    @Override
    public void register(Plugin plugin, GemManager gemManager) {
        this.gemManager = gemManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        var gem = gemManager.getEquippedGem(event.getPlayer());
        if (gem == null || !gem.getId().equals("fire")) return;

        Material drop = SMELT_MAP.get(event.getBlock().getType());
        if (drop == null) return;

        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(
            event.getBlock().getLocation(), new ItemStack(drop));
    }
}
