package com.wasted_ticks.feathermarket.config;

import com.wasted_ticks.feathermarket.FeatherMarket;
import net.md_5.bungee.api.ChatColor;

public class MarketConfig {

    private final FeatherMarket plugin;

    private ChatColor colourError;
    private ChatColor colourConfirm;
    private ChatColor colourTitle;
    private ChatColor colourValue;
    private ChatColor colourText;

    public MarketConfig(FeatherMarket plugin) {
        this.plugin = plugin;
        this.loadConfigValues();
    }

    private void loadConfigValues() {
        this.setColourConfirm();
        this.setColourError();
        this.setColourText();
        this.setColourTitle();
        this.setColourValue();
    }

    private void setColourError(){
        this.colourError = ChatColor.of(plugin.getConfig().getString("colour-codes.error"));
    }
    private void setColourConfirm(){
        this.colourConfirm = ChatColor.of(plugin.getConfig().getString("colour-codes.confirm"));
    }
    private void setColourTitle(){
        this.colourTitle = ChatColor.of(plugin.getConfig().getString("colour-codes.title"));
    }
    private void setColourValue(){
        this.colourValue = ChatColor.of(plugin.getConfig().getString("colour-codes.value"));
    }
    private void setColourText(){
        this.colourText = ChatColor.of(plugin.getConfig().getString("colour-codes.text"));
    }

    public ChatColor getColourError() {
        return colourError;
    }

    public ChatColor getColourConfirm() {
        return colourConfirm;
    }

    public ChatColor getColourTitle() {
        return colourTitle;
    }

    public ChatColor getColourValue() {
        return colourValue;
    }

    public ChatColor getColourText() {
        return colourText;
    }


}
