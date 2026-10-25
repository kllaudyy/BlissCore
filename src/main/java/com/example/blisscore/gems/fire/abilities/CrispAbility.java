package com.example.blisscore.gems.fire.abilities;

import com.example.blisscore.core.Ability;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Map;

public class CrispAbility implements Ability {
    private static final Map<Material, Material> REPLACE_MAP = Map.ofEntries(
        Map.entry(Material.GRASS_BLOCK, Material.NETHERRACK),
        Map.entry(Material.DIRT, Material.NETHERRACK),
        Map.entry(Material.STONE, Material.BLACKSTONE),
        Map.entry(Material.OAK_LOG, Material.CRIMSON_STEM),
        Map.entry(Material.WATER, Material.AIR)
    );

    @Override public String getId() { return "fire_crisp"; }
    @Override public String getDisplayName() { return "Crisp"; }
    @Override public long getCooldownMillis() { return 60_000L; }

    @Override
    public boolean activate(Player player) {
        Location center = player.getLocation();
        int replaced = 0;
        int radius = 10;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x*x + y*y + z*z > radius*radius) continue;
                    Block block = center.getWorld().getBlockAt(
                        center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
                    Material replacement = REPLACE_MAP.get(block.getType());
                    if (replacement != null) {
                        block.setType(replacement);
                        replaced++;
                    }
                }
            }
        }
        player.sendMessage("§6[Crisp] §eПревращено блоков: " + replaced);
        return true;
    }
}
