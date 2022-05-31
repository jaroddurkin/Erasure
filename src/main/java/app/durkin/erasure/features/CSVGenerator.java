package app.durkin.erasure.features;

import app.durkin.erasure.db.SQLite;
import org.bukkit.Statistic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVGenerator {

    public static void generateStatistics(SQLite db) {
        StatisticsCalculator stats = new StatisticsCalculator(db);
        String csvName = "data_" + db.getLatestWorldName() + ".csv";
        try {
            FileWriter csvWriter = new FileWriter(csvName);
            csvWriter.write("uuid,name,deaths,playTime,kills\n");
            Map<String, String> players = db.getAllExistingPlayers();
            for (Map.Entry<String, String> player : players.entrySet()) {
                String uuid = player.getValue();
                String name = player.getKey();
                int deaths = db.getNumberOfDeathsForPlayer(name);
                int playTime = stats.getStatisticForSinglePlayer(name, Statistic.PLAY_ONE_MINUTE);
                int kills = stats.getStatisticForSinglePlayer(name, Statistic.MOB_KILLS);
                String line = uuid + "," + name + "," + deaths + "," + playTime + "," + kills + "\n";
                csvWriter.write(line);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
