package app.durkin.erasure.commands;

import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.events.Messenger;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.command.CommandSender;

public class CancelCommand extends Command {

    private DeathTracker deathTracker;
    private ServerResetHandler resetHandler;

    public CancelCommand(CommandSender sender, String[] args, DeathTracker deathTracker, ServerResetHandler resetHandler) {
        super(sender, args);
        this.deathTracker = deathTracker;
        this.resetHandler = resetHandler;
    }

    public boolean handleCommand() {
        CommandSender sender = getSender();

        if (!sender.hasPermission("erasure.cancel")) {
            Messenger.invalidPermissions(sender);
            return false;
        }

        if (!this.deathTracker.isServerResetting()) {
            Messenger.invalidArguments(sender);
            return false;
        }

        this.resetHandler.cancelRestartTaskIfExists(this.deathTracker.getTaskId());
        this.deathTracker.toggleServerReset();
        Messenger.cancelResetMessage(sender);
        return true;
    }
}
