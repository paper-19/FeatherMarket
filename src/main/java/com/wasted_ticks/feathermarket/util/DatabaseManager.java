package com.wasted_ticks.feathermarket.util;

import com.wasted_ticks.feathermarket.FeatherMarket;

import java.io.File;
import java.sql.*;

public class DatabaseManager {

    private static Connection connection;
    private FeatherMarket plugin;
    private File file;

    public DatabaseManager(FeatherMarket plugin) {
        this.plugin = plugin;
        this.initConnection();
        this.initTables();
        this.close();
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
            plugin.getLog().info("[FeatherMarket] Creating feather_market table.");
            String query = "CREATE TABLE IF NOT EXISTS `feather_market` ("
                + " `id` INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " `seller_uuid` VARCHAR(255) NOT NULL, "
                + " `item` VARCHAR(255) NOT NULL, "
                + " `amount` INT NOT NULL, "
                + " `price` double(64,2) NOT NULL, "
                + " `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";
            try {
                if(!connection.isClosed()) {
                    connection.createStatement().execute(query);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                plugin.getLog().severe("[FeatherMarket] Unable to create feather_market table.");
            }
        }

        if(!this.existsTable("feather_transaction_log")) {
            plugin.getLog().info("[FeatherMarket] Creating feather_transaction_log table.");
            String query = "CREATE TABLE IF NOT EXISTS `feather_transaction_log` ("
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
                plugin.getLog().severe("[FeatherMarket] Unable to create feather_transaction_log table.");
            }

        }
    }


}
