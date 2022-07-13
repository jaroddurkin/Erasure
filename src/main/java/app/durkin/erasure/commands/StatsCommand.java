package app.durkin.erasure.commands;

import app.durkin.erasure.events.Messenger;
import app.durkin.erasure.db.SQLite;
import app.durkin.erasure.features.StatisticsCalculator;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;

public class StatsCommand extends Command {

    private SQLite db;
    private StatisticsCalculator statisticsCalculator;

    public StatsCommand(CommandSender sender, String[] args, SQLite db, StatisticsCalculator statisticsCalculator) {
        super(sender, args);
        this.db = db;
        this.statisticsCalculator = statisticsCalculator;
    }

    public boolean handleCommand() {
        String[] args = getArguments();
        CommandSender sender = getSender();
        if (args.length == 1) {
            int numDeaths = this.db.getNumberOfDeathsForPlayer(sender.getName());
            int ticksPlayed = this.statisticsCalculator.getStatisticForSinglePlayer(sender.getName(), Statistic.PLAY_ONE_MINUTE);
            // convert number of ticks to minutes
            int minutesPlayed = ((ticksPlayed / 20) / 60);
            int mobsKilled = this.statisticsCalculator.getStatisticForSinglePlayer(sender.getName(), Statistic.MOB_KILLS);
            Messenger.statsMessage(sender, numDeaths, minutesPlayed / 60, minutesPlayed % 60, mobsKilled, sender.getName());
            return true;
        } else if (args.length == 2) {
            int numDeaths = this.db.getNumberOfDeathsForPlayer(args[1]);
            int ticksPlayed = this.statisticsCalculator.getStatisticForSinglePlayer(args[1], Statistic.PLAY_ONE_MINUTE);
            // convert number of ticks to minutes
            int minutesPlayed = ((ticksPlayed / 20) / 60) / 60;
            int mobsKilled = this.statisticsCalculator.getStatisticForSinglePlayer(sender.getName(), Statistic.MOB_KILLS);
            Messenger.statsMessage(sender, numDeaths, minutesPlayed / 60, minutesPlayed % 60, mobsKilled, args[1]);
            return true;
        } else {
            Messenger.invalidArguments(sender);
            return false;
        }
    }
}
