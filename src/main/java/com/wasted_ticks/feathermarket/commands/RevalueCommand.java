package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.api.MarketAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RevalueCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public RevalueCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //args[0] == revalue
        //args[1] == item_name (autocomplete with current price+amount)
                    // revalue diamond_sword
                    // revalue "diamond sword" (p:)
        //args[2] ==

        sender.sendMessage("revalue command");
        return false;
    }
}
