package app.durkin.erasure;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    private SQLite db;
    private DeathTracker deathTracker;

    public CommandHandler(SQLite db, DeathTracker deathTracker) {
        this.db = db;
        this.deathTracker = deathTracker;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (args[0].equals("stats")) {
            if (args.length == 1) {
                int numDeaths = this.db.getNumberOfDeathsForPlayer(commandSender.getName());
                Messenger.statsMessage(commandSender, numDeaths, commandSender.getName());
                return true;
            } else if (args.length == 2){
                int numDeaths = this.db.getNumberOfDeathsForPlayer(args[1]);
                Messenger.statsMessage(commandSender, numDeaths, args[1]);
                return true;
            } else {
                Messenger.invalidArguments(commandSender);
                return false;
            }
        }
        if (args.length == 1 && args[0].equals("cancel")) {
            if (!commandSender.hasPermission("erasure.cancel")) {
                Messenger.invalidPermissions(commandSender);
                return false;
            }
            if (!this.deathTracker.isServerResetting()) {
                Messenger.invalidArguments(commandSender);
            } else {
                this.deathTracker.toggleServerReset();
                Messenger.cancelResetMessage(commandSender);
            }
            return true;
        }
        return false;
    }
}
