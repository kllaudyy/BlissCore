package com.example.blisscore.commands;

import com.example.blisscore.BlissCore;
import com.example.blisscore.manager.GemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrustSubCommand implements SubCommand {
    
    private final BlissCore plugin;
    private final GemManager gemManager;

    public TrustSubCommand(BlissCore plugin) { 
        this.plugin = plugin;
        this.gemManager = plugin.getGemManager();
    }

    @Override public String getName() { return "trust"; }
    @Override public String getPermission() { return "bliss.user"; }
    @Override public String getUsage() { return "/bliss trust <ник> или /bliss untrust <ник>"; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }
        
        if (args.length < 2) {
            player.sendMessage("§cИспользование: " + getUsage());
            return true;
        }

        boolean isAdding = args[0].equalsIgnoreCase("trust");
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            player.sendMessage("§cИгрок не найден в сети!");
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage("§cВы не можете доверять самому себе (вы и так себе не навредите)!");
            return true;
        }

        if (isAdding) {
            gemManager.getData(player).addTrusted(target.getUniqueId());
            player.sendMessage("§aВы теперь доверяете §e" + target.getName() + "§a. Ваши способности не будут наносить им урон.");
        } else {
            gemManager.getData(player).removeTrusted(target.getUniqueId());
            player.sendMessage("§cВы больше не доверяете §e" + target.getName() + "§c. Осторожнее с магией!");
        }

        // НОВОЕ: Сохраняем изменения списка друзей на диск
        plugin.getDataManager().saveData(gemManager.getData(player));
        
        return true;
    }
}
