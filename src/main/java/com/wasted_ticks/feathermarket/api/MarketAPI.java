package com.wasted_ticks.feathermarket.api;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.data.MarketItem;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MarketAPI {

    private FeatherMarket plugin;

    public MarketAPI(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    public List<MarketItem> getOffersByOfflinePlayer(OfflinePlayer player){
        return MarketItem.where("seller_uuid = '" + player.getUniqueId().toString() + "'");
    }

    public List<MarketItem> getOffers() {
        return MarketItem.findAll().orderBy("material asc");
    }

    public boolean postItem(OfflinePlayer player, ItemStack stack, double price) {

        MarketItem item = new MarketItem();

        item.setString("seller_uuid", player.getUniqueId().toString());
        item.setString("material", stack.getType().toString());
        item.setInteger("amount", stack.getAmount());
        item.setDouble("price", price);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(baos);
            bukkitOutputStream.writeObject(stack);
            bukkitOutputStream.close();
        } catch (IOException e) {
        }
        String stackData = Base64Coder.encodeLines(baos.toByteArray());

        item.setString("item", stackData);

        return item.saveIt();
    }

}
