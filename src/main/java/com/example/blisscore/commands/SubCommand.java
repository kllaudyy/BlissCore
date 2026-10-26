package com.example.blisscore.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String getName();
    String getPermission();
    String getUsage();
    boolean execute(CommandSender sender, String[] args);
}
