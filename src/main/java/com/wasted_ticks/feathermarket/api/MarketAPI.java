package com.wasted_ticks.feathermarket.api;

import com.wasted_ticks.feathermarket.FeatherMarket;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MarketAPI {

    private FeatherMarket plugin;

    public MarketAPI(FeatherMarket plugin) {
        this.plugin = plugin;
    }

    public List<ItemStack> getOffersByOfflinePlayer(OfflinePlayer player){

        String uuid = player.getUniqueId().toString();
        String query = "SELECT * FROM feather_market WHERE seller_uuid='" + uuid + "' ORDER BY id DESC;";
        try {
            ResultSet rs = plugin.getDatabase().getConnection().prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
