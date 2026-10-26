package com.example.blisscore.commands;

import com.example.blisscore.BlissCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class BlissCommand implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public BlissCommand(BlissCore plugin) {
        register(new GiveSubCommand(plugin.getGemManager()));
        register(new SmpStartSubCommand(plugin));
        // НОВОЕ: Регистрируем Trust (он ловит и trust, и untrust)
        register(new TrustSubCommand(plugin.getGemManager()));
    }

    private void register(SubCommand cmd) { subCommands.put(cmd.getName().toLowerCase(), cmd); }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6=== BlissCore ===");
            sender.sendMessage("§e/bliss give <player> <gem>");
            sender.sendMessage("§e/bliss smp start");
            sender.sendMessage("§e/bliss trust <player> / untrust <player>");
            return true;
        }

        // Если игрок ввел "untrust", мы перенаправляем его на "trust" команду
        String subName = args[0].toLowerCase();
        if (subName.equals("untrust")) {
            subName = "trust";
        }

        SubCommand sub = subCommands.get(subName);
        if (sub == null) { sender.sendMessage("§cНеизвестная подкоманда."); return true; }
        if (!sender.hasPermission(sub.getPermission())) { sender.sendMessage("§cНет прав!"); return true; }
        
        return sub.execute(sender, args);
    }
}
