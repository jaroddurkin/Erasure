package app.durkin.erasure.commands;

import app.durkin.erasure.Erasure;
import app.durkin.erasure.events.Messenger;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.command.CommandSender;

public class ResetCommand extends Command {

    private DeathTracker deathTracker;
    private ServerResetHandler resetHandler;
    private Erasure plugin;

    public ResetCommand(CommandSender sender, String[] args, DeathTracker deathTracker, ServerResetHandler resetHandler, Erasure plugin) {
        super(sender, args);
        this.deathTracker = deathTracker;
        this.resetHandler = resetHandler;
        this.plugin = plugin;
    }

    public boolean handleCommand() {
        CommandSender sender = getSender();

        if (!sender.hasPermission("erasure.reset")) {
            Messenger.invalidPermissions(sender);
            return false;
        }

        if (this.deathTracker.isServerResetting()) {
            Messenger.invalidArguments(sender);
            return false;
        }

        this.deathTracker.toggleServerReset();
        this.resetHandler.scheduleServerRestart(this.plugin);
        Messenger.sendDeathMessage(sender.getName(), true);
        return true;
    }
}
