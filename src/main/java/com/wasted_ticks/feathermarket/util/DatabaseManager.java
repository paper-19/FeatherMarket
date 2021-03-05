package com.wasted_ticks.feathermarket.util;

import com.wasted_ticks.feathermarket.FeatherMarket;
import com.wasted_ticks.feathermarket.data.MarketItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.javalite.activejdbc.Base;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DatabaseManager {

    private static Connection connection;
    private FeatherMarket plugin;
    private File file;

    public DatabaseManager(FeatherMarket plugin) {
        this.plugin = plugin;
        this.initConnection();
        this.initTables();
    }


    public Connection getConnection() {
        try {
            if(connection.isClosed()) {
                this.initConnection();
            }
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherMarket] Unable to receive connection.");
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                //close javalite connection
                Base.close();
                connection.close();
            } catch (SQLException e) {
                plugin.getLog().severe("[FeatherMarket] Unable to close DatabaseManager connection.");
            }
        }
    }

    private void initConnection() {
        File folder = this.plugin.getDataFolder();
        if(!folder.exists()) {
            folder.mkdir();
        }
        this.file = new File(folder.getAbsolutePath() + File.separator +  "FeatherMarket.db");

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file.getAbsolutePath());
            // init javalite connection
            Base.attach(this.connection);
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherMarket] Unable to initialize DatabaseManager connection.");
        }
    }

    private boolean existsTable(String table) {
        try {
            if(!connection.isClosed()) {
                ResultSet rs = connection.getMetaData().getTables(null, null, table, null);
                return rs.next();
            } else {
                return false;
            }
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherMarket] Unable to query table metadata.");
            return false;
        }
    }

    private void initTables() {
        if(!this.existsTable("feather_market")) {
            plugin.getLog().info("[FeatherMarket] Creating market_items table.");
            String query = "CREATE TABLE IF NOT EXISTS `market_items` ("
                + " `id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " `seller_uuid` VARCHAR(255) NOT NULL, "
                + " `item` VARCHAR(255) NOT NULL, "
                + " `material` VARCHAR(255) NOT NULL, "
                + " `amount` INT NOT NULL, "
                + " `price` double(64,2) NOT NULL, "
                + " `active` INT NOT NULL DEFAULT 1, "
                + " `cancelling` INT NOT NULL DEFAULT 0, "
                + " `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";
            try {
                if(!connection.isClosed()) {
                    connection.createStatement().execute(query);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                plugin.getLog().severe("[FeatherMarket] Unable to create market_items table.");
            }
        }

        if(!this.existsTable("feather_transaction_log")) {
            plugin.getLog().info("[FeatherMarket] Creating market_transactions table.");
            String query = "CREATE TABLE IF NOT EXISTS `market_transactions` ("
                + " `id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " `seller_uuid` VARCHAR(255) NOT NULL, "
                + " `buyer_uuid` VARCHAR(255) NOT NULL, "
                + " `item` VARCHAR(255) NOT NULL, "
                + " `amount` INT NOT NULL, "
                + " `price` double(64,2) NOT NULL, "
                + " `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";
            try {
                if(!connection.isClosed()) {
                    connection.createStatement().execute(query);
                }
            } catch (SQLException e) {
                plugin.getLog().severe("[FeatherMarket] Unable to create market_transactions table.");
            }

        }
    }


}
