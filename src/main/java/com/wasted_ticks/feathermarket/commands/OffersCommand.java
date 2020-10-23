package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OffersCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public OffersCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("offers command");
        return false;
    }
}