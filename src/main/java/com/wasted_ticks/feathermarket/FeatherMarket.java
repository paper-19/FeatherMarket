package com.wasted_ticks.feathermarket;

import com.avaje.ebean.validation.NotNull;
import com.wasted_ticks.feathermarket.api.MarketAPI;
import com.wasted_ticks.feathermarket.commands.*;
import com.wasted_ticks.feathermarket.config.MarketConfig;
import com.wasted_ticks.feathermarket.util.DatabaseManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;

public final class FeatherMarket extends JavaPlugin {

    private static final Logger logger = Logger.getLogger("Minecraft");
    private static FeatherMarket plugin;
    private static Economy economy;
    private static MarketAPI api;
    private static MarketConfig config;
    private static DatabaseManager database;

    @Override
    public void onEnable() {
        // Plugin startup logic
//        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.setupEconomy();
        this.registerCommands();

        plugin = this;
        api = new MarketAPI(plugin);
        config = new MarketConfig(plugin);
        database = new DatabaseManager(plugin);

    }

    public Logger getLog() {
        return logger;
    }

    public MarketConfig getMarketConfig() {
        return config;
    }

    public MarketAPI getMarketAPI() {
        return api;
    }

    public Economy getEconomy() {
        return economy;
    }

    public DatabaseManager getDatabase() {
        return database;
    }

    private void registerCommands() {

        Handler handler = new Handler();

        handler.register("market", (CommandExecutor) new MarketCommand(plugin));
        handler.register("reload", (CommandExecutor) new ReloadCommand(plugin));
        handler.register("sell", (CommandExecutor) new SellCommand(plugin));
        handler.register("revalue", (CommandExecutor) new RevalueCommand(plugin));
        handler.register("restock", (CommandExecutor) new RestockCommand(plugin));
        handler.register("help", (CommandExecutor) new HelpCommand(plugin));
        handler.register("offers", (CommandExecutor) new OffersCommand(plugin));
        handler.register("buy", (CommandExecutor) new BuyCommand(plugin));

        this.getCommand("market").setExecutor(handler);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    /**
     * VAULT
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null)
            return false;
        economy = (Economy)registeredServiceProvider.getProvider();
        return (economy != null);
    }
}
