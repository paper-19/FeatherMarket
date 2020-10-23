package com.wasted_ticks.feathermarket.commands;

import com.sun.tools.javac.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Handler implements CommandExecutor {

    private static HashMap<String, CommandExecutor> commands = new HashMap<>();

    public void register(String subCommand, CommandExecutor executor) {
        commands.put(subCommand, executor);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            commands.get("market").onCommand(sender, command, label, args);
            return true;
        } else {
            if(commands.containsKey(args[0])) {
                commands.get(args[0]).onCommand(sender, command, label, args);
                return true;
            } else {
                List<String> list = List.from(args);
                if(list.stream().allMatch(StringUtils::isAlpha)) {
                    commands.get("market").onCommand(sender, command, label, args);
                } else {
                    sender.sendMessage("Err.");
                }
                return true;
            }
        }
    }
}
