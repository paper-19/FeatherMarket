package com.wasted_ticks.feathermarket.inventories;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.data.MarketItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OffersInventoryProvider implements InventoryProvider {

    private Player player;
    private FeatherMarket plugin;

    public OffersInventoryProvider(Player player, FeatherMarket plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        List<ClickableItem> items = new ArrayList<>();

        List<MarketItem> offers = plugin.getMarketAPI().getOffersByOfflinePlayer(player);

        for (MarketItem item : offers) {

            String base64 = item.getString("item");
            int offerID = item.getInteger("id");

            ItemStack stack;

            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
                BukkitObjectInputStream bois = new BukkitObjectInputStream(byteArrayInputStream);
                stack = (ItemStack) bois.readObject();
                bois.close();
            } catch (IOException | ClassNotFoundException e) {
                this.plugin.getServer().getConsoleSender().sendMessage("Exception deserializing ItemStack");
                continue;
            }

            ItemMeta meta = stack.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_RED + "Click to cancel offer.");
            meta.setLore(lore);
            stack.setItemMeta(meta);
            items.add(ClickableItem.of(stack, e -> {
                e.setCancelled(true);
//                player.sendMessage("ID of ItemStack clicked: " + offerID);
                player.closeInventory(); // will this work?
                player.sendMessage("Cancelled the offer selected, wait 72 hours before retrieving from \"/market offers\" inventory.");
            }));
        }

        for (ClickableItem item: items) {
            contents.add(item);
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static SmartInventory getInventory(Player player, FeatherMarket plugin) {
        return SmartInventory.builder()
                .provider(new OffersInventoryProvider(player, plugin))
                .title(ChatColor.DARK_GREEN + player.getDisplayName() + "'s offers")
                .size(3,9)
                .build();
    }

}
