package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.api.MarketAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor {

    private FeatherMarket plugin;

    public SellCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(validArgs(sender, args)) {
            sender.sendMessage("Valid args");
        }
        Player player = (Player) sender;
        ItemStack stack = player.getInventory().getItemInMainHand();

        //i am here

        //args[0] = sell
        //args[1] = price_per_item
        //args[2] = boolean for all


        sender.sendMessage("sell command");
        return false;
    }

    private boolean validArgs(CommandSender sender, String[] args) {

        switch (args.length) {
            case 3:
                if(!args[2].equalsIgnoreCase("all")) {
                    sender.sendMessage("Invalid second argument supplied.");
                    break;
                }
            case 2:
                String stringPrice = args[1];
                if(stringPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    try{
                        double pricePer = Double.parseDouble(stringPrice);
                        if(pricePer != 0) {
                            return true;
                        } else {
                            sender.sendMessage("Invalid price argument.");
                            break;
                        }
                    } catch(NumberFormatException e) {
                        sender.sendMessage("Unable to parse supplied amount.");
                        break;
                    }
                }
            default: sender.sendMessage("Invalid amount of arguments supplied.");
        }

        return false;

    }
}