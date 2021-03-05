package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReloadCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public ReloadCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("reload command");

        Player player = (Player) sender;
        ItemStack stack = player.getInventory().getItemInMainHand();

        if(stack.hasItemMeta()) {
            player.sendMessage(stack.getItemMeta().toString());
        } else {
            player.sendMessage("No Item Meta");
        }

        return false;
    }
}