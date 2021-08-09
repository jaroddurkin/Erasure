package app.durkin.erasure.commands;

import app.durkin.erasure.Messenger;
import app.durkin.erasure.SQLite;
import org.bukkit.command.CommandSender;

public class StatsCommand extends Command {

    private SQLite db;

    public StatsCommand(CommandSender sender, String[] args, SQLite db) {
        super(sender, args);
        this.db = db;
    }

    public boolean handleCommand() {
        String[] args = getArguments();
        CommandSender sender = getSender();
        if (args.length == 1) {
            int numDeaths = this.db.getNumberOfDeathsForPlayer(sender.getName());
            Messenger.statsMessage(sender, numDeaths, sender.getName());
            return true;
        } else if (args.length == 2) {
            int numDeaths = this.db.getNumberOfDeathsForPlayer(args[1]);
            Messenger.statsMessage(sender, numDeaths, args[1]);
            return true;
        } else {
            Messenger.invalidArguments(sender);
            return false;
        }
    }
}
