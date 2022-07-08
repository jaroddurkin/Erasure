package app.durkin.erasure.commands;

import app.durkin.erasure.config.ConfigManager;
import app.durkin.erasure.events.Messenger;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class ConfigCommand extends Command {

    private ConfigManager configManager;

    public ConfigCommand(CommandSender sender, String[] args, ConfigManager configManager) {
        super(sender, args);
        this.configManager = configManager;
    }

    public boolean handleCommand() {
        CommandSender sender = getSender();
        String[] args = getArguments();

        if (!sender.hasPermission("erasure.config")) {
            Messenger.invalidPermissions(sender);
            return false;
        }

        if (args.length < 2) {
            Messenger.invalidArguments(sender);
            return false;
        }

        if (args[1].equalsIgnoreCase("set")) {
            if (configSet()) {
                Messenger.configSuccess(sender);
                return true;
            }
            return false;
        }
        if (args[1].equalsIgnoreCase("get")) {
            return configGet();
        }

        Messenger.invalidArguments(sender);
        return false;
    }

    private boolean configSet() {
        CommandSender sender = getSender();
        String[] args = getArguments();

        if (args.length != 4) {
            Messenger.invalidArguments(sender);
            return false;
        }
        if (args[2].equalsIgnoreCase("resetTime")) {
            if (isNumber(args[3]) && Integer.parseInt(args[3]) > -1 && Integer.parseInt(args[3]) < 1441) {
                try {
                    this.configManager.setResetTimeInMinutes(Integer.parseInt(args[3]));
                } catch(IOException e) {
                    e.printStackTrace();
                    Messenger.serverError(sender);
                    return false;
                }
                return true;
            }
        } else if (args[2].equalsIgnoreCase("deleteWorld")) {
            if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                try {
                    boolean reset = args[3].equalsIgnoreCase("true");
                    this.configManager.setDeleteOnReset(reset);
                } catch(IOException e) {
                    e.printStackTrace();
                    Messenger.serverError(sender);
                    return false;
                }
                return true;
            }
        } else if (args[2].equalsIgnoreCase("messageOnDeath")) {
            if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                try {
                    boolean message = args[3].equalsIgnoreCase("true");
                    this.configManager.setMessageOnDeath(message);
                } catch(IOException e) {
                    e.printStackTrace();;
                    Messenger.serverError(sender);
                    return false;
                }
                return true;
            }
        }
        Messenger.invalidArguments(sender);
        return false;
    }

    private boolean configGet() {
        CommandSender sender = getSender();
        String[] args = getArguments();

        if (args.length != 3) {
            Messenger.invalidArguments(sender);
            return false;
        }

        if (args[2].equalsIgnoreCase("resetTime")) {
            int resetTime = -1;
            try {
                resetTime = this.configManager.getResetTimeInMinutes();
            } catch(IOException e) {
                e.printStackTrace();
                Messenger.serverError(sender);
                return false;
            }
            if (resetTime == -1) {
                Messenger.serverError(sender);
                return false;
            }
            Messenger.configValue(sender, Integer.toString(resetTime));
            return true;
        } else if (args[2].equalsIgnoreCase("deleteWorld")) {
            boolean delete;
            try {
                delete = this.configManager.getDeleteOnReset();
            } catch(IOException e) {
                e.printStackTrace();
                Messenger.serverError(sender);
                return false;
            }
            Messenger.configValue(sender, Boolean.toString(delete));
            return true;
        } else if (args[2].equalsIgnoreCase("messageOnDeath")) {
            boolean message;
            try {
                message = this.configManager.getMessageOnDeath();
            } catch(IOException e) {
                e.printStackTrace();
                Messenger.serverError(sender);
                return false;
            }
            Messenger.configValue(sender, Boolean.toString(message));
            return true;
        }

        Messenger.invalidArguments(sender);
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
