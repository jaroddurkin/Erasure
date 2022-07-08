package app.durkin.erasure.events;

import app.durkin.erasure.*;
import app.durkin.erasure.config.ConfigManager;
import app.durkin.erasure.db.SQLite;
import app.durkin.erasure.features.CSVGenerator;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class DeathListener implements Listener {

    private SQLite db;
    private DeathTracker deathTracker;
    private ServerResetHandler serverResetHandler;
    private Erasure plugin;
    private ConfigManager configManager;

    public DeathListener(SQLite db, DeathTracker deathTracker, ServerResetHandler serverResetHandler, Erasure plugin, ConfigManager configManager) {
        this.db = db;
        this.deathTracker = deathTracker;
        this.serverResetHandler = serverResetHandler;
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) throws IOException {
        if (!deathTracker.isServerResetting()) {
            this.db.addDeathToTable(event.getEntity().getDisplayName(), event.getEntity().getLastDamageCause().getEventName());
            deathTracker.toggleServerReset();
            serverResetHandler.scheduleServerRestart(this.plugin);
            if (configManager.getMessageOnDeath()) {
                Messenger.sendDeathMessage(event.getEntity().getDisplayName());
            }
        }
    }
}
