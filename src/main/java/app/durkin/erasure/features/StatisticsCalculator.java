package app.durkin.erasure.features;

import app.durkin.erasure.db.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatisticsCalculator {

    private SQLite db;

    public StatisticsCalculator(SQLite db) {
        this.db = db;
    }

    public int getStatisticForSinglePlayer(String playerName, Statistic stat) {
        String playerUUID = this.db.getPlayerUUID(playerName);
        Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));
        if (player == null) {
            OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
            return offPlayer.getStatistic(stat);
        }
        return player.getStatistic(stat);
    }

    public Map<String, Integer> getStatisticForAllPlayers(Statistic stat) {
        Map<String, Integer> statistic = new HashMap<>();
        Map<String, String> allPlayers = this.db.getAllExistingPlayers();
        for (String key : allPlayers.keySet()) {
            Player player = Bukkit.getPlayer(UUID.fromString(allPlayers.get(key)));
            if (player == null) {
                OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(UUID.fromString(allPlayers.get(key)));
                statistic.put(key, offPlayer.getStatistic(stat));
            } else {
                statistic.put(key, player.getStatistic(stat));
            }
        }
        return statistic;
    }
}
