package app.durkin.erasure.commands;

import app.durkin.erasure.Erasure;
import app.durkin.erasure.config.ConfigManager;
import app.durkin.erasure.db.SQLite;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import app.durkin.erasure.features.StatisticsCalculator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    private SQLite db;
    private DeathTracker deathTracker;
    private ServerResetHandler serverResetHandler;
    private ConfigManager configManager;
    private StatisticsCalculator statisticsCalculator;
    private Erasure plugin;

    public CommandHandler(SQLite db, DeathTracker deathTracker, ServerResetHandler serverResetHandler, ConfigManager configManager, StatisticsCalculator statisticsCalculator, Erasure plugin) {
        this.db = db;
        this.deathTracker = deathTracker;
        this.serverResetHandler = serverResetHandler;
        this.configManager = configManager;
        this.statisticsCalculator = statisticsCalculator;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            return false;
        }
        String parentCommand = args[0];
        if (parentCommand.equals("stats")) {
            StatsCommand commandHandler = new StatsCommand(commandSender, args, this.db, this.statisticsCalculator);
            return commandHandler.handleCommand();
        }
        if (parentCommand.equals("cancel")) {
            CancelCommand commandHandler = new CancelCommand(commandSender, args, this.deathTracker, this.serverResetHandler);
            return commandHandler.handleCommand();
        }
        if (parentCommand.equals("config")) {
            ConfigCommand commandHandler = new ConfigCommand(commandSender, args, this.configManager);
            return commandHandler.handleCommand();
        }
        if (parentCommand.equals("reset")) {
            ResetCommand commandHandler = new ResetCommand(commandSender, args, this.deathTracker, this.serverResetHandler, this.plugin);
            return commandHandler.handleCommand();
        }
        return false;
    }
}
