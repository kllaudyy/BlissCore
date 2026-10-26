package com.example.blisscore.commands;

import com.example.blisscore.BlissCore;
import com.example.blisscore.data.PlayerGemData;
import com.example.blisscore.manager.GemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnergySubCommand implements SubCommand {
    private final BlissCore plugin;
    private final GemManager gemManager;

    public EnergySubCommand(BlissCore plugin) {
        this.plugin = plugin;
        this.gemManager = plugin.getGemManager();
    }

    @Override public String getName() { return "energy"; }
    @Override public String getPermission() { return "bliss.admin"; }
    @Override public String getUsage() { return "/bliss energy <player> <set|add|remove> <amount>"; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("§cИспользование: " + getUsage());
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cИгрок не найден!");
            return true;
        }

        String action = args[2].toLowerCase();
        int amount;

        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cЧисло введено неверно!");
            return true;
        }

        PlayerGemData data = gemManager.getData(target);

        switch (action) {
            case "set":
                data.setEnergy(amount);
                break;
            case "add":
                data.addEnergy(amount);
                break;
            case "remove":
                data.addEnergy(-amount);
                break;
            default:
                sender.sendMessage("§cНеизвестное действие. Используйте set, add или remove.");
                return true;
        }

        // Сохраняем на диск
        plugin.getDataManager().saveData(data);
        
        sender.sendMessage("§aЭнергия игрока " + target.getName() + " теперь: " + data.getEnergy() + "/10");
        target.sendMessage("§eВаша энергия изменилась! Теперь: " + data.getEnergy() + "/10");
        return true;
    }
}
