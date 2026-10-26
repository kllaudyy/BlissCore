package com.example.blisscore.commands;

import com.example.blisscore.BlissCore;
import com.example.blisscore.core.GemDefinition;
import com.example.blisscore.core.Rarity;
import com.example.blisscore.core.Tier;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class SmpStartSubCommand implements SubCommand {
    private final BlissCore plugin;
    private final Random random = new Random();

    public SmpStartSubCommand(BlissCore plugin) { this.plugin = plugin; }

    @Override public String getName() { return "smp"; }
    @Override public String getPermission() { return "bliss.admin"; }
    @Override public String getUsage() { return "/bliss smp start"; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2 || !args[1].equalsIgnoreCase("start")) {
            sender.sendMessage("§cИспользование: " + getUsage()); return true;
        }

        plugin.getConfig().set("smp.started", true);
        plugin.saveConfig();

        List<GemDefinition> starterGems = plugin.getGemManager().getAllGems().stream()
                .filter(g -> g.getRarity() == Rarity.BASIC && g.getTier() == Tier.T1).toList();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (plugin.getGemManager().getData(p).getGemId() == null) {
                GemDefinition gem = starterGems.get(random.nextInt(starterGems.size()));
                plugin.getGemManager().equipGem(p, gem.getId());
                p.sendMessage("§a§lSMP стартовал! §aВаш первый гем: §e" + gem.getDisplayName());
            }
        }
        sender.sendMessage("§aSMP запущен!");
        return true;
    }
}
