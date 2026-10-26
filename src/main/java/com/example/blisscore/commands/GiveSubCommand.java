package com.example.blisscore.commands;

import com.example.blisscore.manager.GemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSubCommand implements SubCommand {
    private final GemManager gemManager;
    public GiveSubCommand(GemManager gemManager) { this.gemManager = gemManager; }

    @Override public String getName() { return "give"; }
    @Override public String getPermission() { return "bliss.admin"; }
    @Override public String getUsage() { return "/bliss give <player> <gem_id>"; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) { sender.sendMessage("§cИспользование: " + getUsage()); return true; }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) { sender.sendMessage("§cИгрок не найден!"); return true; }
        if (gemManager.getGem(args[2]) == null) { sender.sendMessage("§cГем не существует!"); return true; }
        
        gemManager.equipGem(target, args[2]);
        sender.sendMessage("§aВыдан гем " + args[2] + " игроку " + target.getName());
        return true;
    }
}
