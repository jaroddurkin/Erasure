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

    public static void selfStatsMessage(CommandSender sender, int deaths) {
        sender.sendMessage(PREFIX + "You have died " + deaths + " times.");
    }
}
