package app.durkin.erasure;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private SQLite db;
    private DeathTracker deathTracker;

    public DeathListener(SQLite db, DeathTracker deathTracker) {
        this.db = db;
        this.deathTracker = deathTracker;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!deathTracker.isServerResetting()) {
            this.db.addDeathToTable(event.getEntity().getDisplayName(), event.getEntity().getLastDamageCause().getEventName());
            deathTracker.toggleServerReset();
            Messenger.sendDeathMessage(event.getEntity().getDisplayName());
        }
    }
}
