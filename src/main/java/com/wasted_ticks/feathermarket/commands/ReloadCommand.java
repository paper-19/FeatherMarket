package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.api.MarketAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public ReloadCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("reload command");
        return false;
    }
}