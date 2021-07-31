package app.durkin.erasure;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class CommandHandler implements CommandExecutor {

    private SQLite db;
    private DeathTracker deathTracker;
    private ServerResetHandler serverResetHandler;
    private ConfigManager configManager;

    public CommandHandler(SQLite db, DeathTracker deathTracker, ServerResetHandler serverResetHandler, ConfigManager configManager) {
        this.db = db;
        this.deathTracker = deathTracker;
        this.serverResetHandler = serverResetHandler;
        this.configManager = configManager;
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
                this.serverResetHandler.cancelRestartTaskIfExists(this.deathTracker.getTaskId());
                this.deathTracker.toggleServerReset();
                Messenger.cancelResetMessage(commandSender);
            }
            return true;
        }
        if (args[0].equals("config")) {
            if (!commandSender.hasPermission("erasure.config")) {
                Messenger.invalidPermissions(commandSender);
                return false;
            }
            if (args[1].equals("set")) {
                if (args.length != 4) {
                    Messenger.invalidArguments(commandSender);
                    return false;
                }
                if (args[2].equalsIgnoreCase("resetTime")) {
                    if ((isNumber(args[3]) && Integer.parseInt(args[3]) > -1 && Integer.parseInt(args[3]) < 1441)) {
                        try {
                            configManager.setResetTimeInMinutes(Integer.parseInt(args[3]));
                        } catch(IOException e) {
                            e.printStackTrace();
                            Messenger.serverError(commandSender);
                            return false;
                        }
                        Messenger.configSuccess(commandSender);
                        return true;
                    }
                    Messenger.invalidArguments(commandSender);
                    return false;
                } else if (args[2].equalsIgnoreCase("deleteWorld")) {
                    if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                        try {
                            boolean reset = args[3].equalsIgnoreCase("true");
                            configManager.setDeleteOnReset(reset);
                        } catch(IOException e) {
                            e.printStackTrace();
                            Messenger.serverError(commandSender);
                            return false;
                        }
                    }
                } else {
                    Messenger.invalidArguments(commandSender);
                    return false;
                }
                Messenger.configSuccess(commandSender);
            } else if (args[1].equals("get")) {
                if (args.length != 3) {
                    Messenger.invalidArguments(commandSender);
                    return false;
                }
                if (args[2].equalsIgnoreCase("resetTime")) {
                    int resetTime = -1;
                    try {
                        resetTime = configManager.getResetTimeInMinutes();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Messenger.serverError(commandSender);
                        return false;
                    }
                    if (resetTime == -1) {
                        Messenger.serverError(commandSender);
                        return false;
                    }
                    Messenger.configValue(commandSender, Integer.toString(resetTime));
                } else if (args[2].equalsIgnoreCase("deleteWorld")) {
                    boolean delete;
                    try {
                        delete = configManager.getDeleteOnReset();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Messenger.serverError(commandSender);
                        return false;
                    }
                    Messenger.configValue(commandSender, Boolean.toString(delete));
                } else {
                    Messenger.invalidArguments(commandSender);
                    return false;
                }
            } else {
                Messenger.invalidArguments(commandSender);
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isNumber(String num) {
        if (num == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(num);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
