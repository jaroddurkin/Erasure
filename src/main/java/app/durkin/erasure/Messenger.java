package app.durkin.erasure;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messenger {

    private static String PREFIX = ChatColor.DARK_RED + "[" + ChatColor.RED + "Erasure" + ChatColor.DARK_RED + "]" + ChatColor.WHITE + " ";

    public static void sendDeathMessage(String displayName) {
        Bukkit.broadcastMessage(PREFIX + displayName + " has ruined the game for everyone!");
        Bukkit.broadcastMessage(PREFIX + "The server will reset in two minutes.");
        Bukkit.broadcastMessage(PREFIX + "If this is a big mistake, have an admin use the command: /erasure cancel");
    }

    public static void statsMessage(CommandSender sender, int deaths, String name) {
        String user;
        if (sender.getName().equals(name)) {
            user = "You";
        } else {
            user = name;
        }
        sender.sendMessage(PREFIX + user + " died " + deaths + " times.");
    }

    public static void invalidArguments(CommandSender sender) {
        sender.sendMessage(PREFIX + "Invalid arguments for your command!");
    }

    public static void cancelResetMessage(CommandSender sender) {
        Bukkit.broadcastMessage(PREFIX + sender.getName() + " has cancelled the server reset!");
    }

    public static void invalidPermissions(CommandSender sender) {
        sender.sendMessage(PREFIX + "You do not have permission to perform this!");
    }

    public static void serverError(CommandSender sender) {
        sender.sendMessage(PREFIX + "Internal server error processing your command.");
    }

    public static void configSuccess(CommandSender sender) {
        sender.sendMessage(PREFIX + "Successfully set configuration!");
    }

    public static void configValue(CommandSender sender, String configValue) {
        sender.sendMessage(PREFIX + "Value: " + configValue);
    }
}
