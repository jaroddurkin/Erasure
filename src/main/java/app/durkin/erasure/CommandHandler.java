package app.durkin.erasure;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    private SQLite db;

    public CommandHandler(SQLite db) {
        this.db = db;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1 && args[0].equals("stats")) {
            int numDeaths = this.db.getNumberOfDeathsForPlayer(commandSender.getName());
            Messenger.selfStatsMessage(commandSender, numDeaths);
            return true;
        }
        return false;
    }
}
