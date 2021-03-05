package com.wasted_ticks.feathermarket.inventories;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.data.MarketItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketInventoryProvider implements InventoryProvider {

    private Player player;
    private FeatherMarket plugin;

    public MarketInventoryProvider(Player player, FeatherMarket plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Pagination pagination = contents.pagination();
        List<ClickableItem> items = new ArrayList<>();

        List<MarketItem> offers = plugin.getMarketAPI().getOffers();

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
                continue;
            }

            ItemMeta meta = stack.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_GREEN + "Price for stack" + ChatColor.DARK_GRAY + ": " +  ChatColor.GRAY + item.getDouble("price"));
            lore.add(ChatColor.DARK_GREEN + "Price / quantity" + ChatColor.DARK_GRAY + ": " +  ChatColor.GRAY + String.format("%.3f", item.getDouble("price") / item.getInteger("amount")));
            meta.setLore(lore);
            stack.setItemMeta(meta);

            items.add(ClickableItem.of(stack, e -> {
                player.sendMessage("ID of ItemStack clicked: " + offerID);
                e.setCancelled(true);
            }));

        }

        pagination.setItems(items.toArray(new ClickableItem[0]));
        pagination.setItemsPerPage(45);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        contents.set(5, 0, ClickableItem.of(new ItemStack(Material.PAPER), e -> this.getInventory(player, plugin).open(player, pagination.previous().getPage())));
        contents.set(5, 8, ClickableItem.of(new ItemStack(Material.PAPER), e -> this.getInventory(player, plugin).open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static SmartInventory getInventory(Player player, FeatherMarket plugin) {
        return SmartInventory.builder()
                .provider(new MarketInventoryProvider(player, plugin))
                .title(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Market")
                .build();
    }
}
