package com.wasted_ticks.feathermarket.commands;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.data.MarketItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SellCommand implements CommandExecutor {

    private static FeatherMarket plugin;

    public SellCommand(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!validity(sender, args)){
            sender.sendMessage("Invalid usage: /market sell <price_for_stack>");
            return false;
        }

        Player player = (Player) sender;
        ItemStack stack = player.getInventory().getItemInMainHand();
        double price = Double.parseDouble(args[1]);

        if(stack.getType().isAir()) {
            sender.sendMessage("Hold the item you wish to sell in your main hand.");
            return false;
        }

        List<MarketItem> items = plugin.getMarketAPI().getOffersByOfflinePlayer(player);
        if(items.size() >= 3) {
            sender.sendMessage("You have too many offers in the market currently, cancel some before trying to add more.");
            return false;
        }

        boolean posted = plugin.getMarketAPI().postItem(player, stack, price);
        if(posted) {
            player.getInventory().removeItem(stack);
        }

        return true;
    }

    private boolean validity(CommandSender sender, String[] args) {
        boolean valid = false;
        if(args.length == 2) {
            String stringPrice = args[1];
            if (stringPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                try {
                    double pricePer = Double.parseDouble(stringPrice);
                    if (pricePer != 0) {
                         valid = true;
                    }
                } catch(NumberFormatException e) { }
            }
        }
        return valid;
    }
}