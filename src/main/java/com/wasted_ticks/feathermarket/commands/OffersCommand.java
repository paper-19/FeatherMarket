package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.inventories.MarketInventoryProvider;
import com.wasted_ticks.feathermarket.inventories.OffersInventoryProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OffersCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public OffersCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            OffersInventoryProvider.getInventory(player, plugin).open(player);
        }
        return true;
    }
}