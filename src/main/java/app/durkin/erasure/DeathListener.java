package app.durkin.erasure;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private SQLite db;

    public DeathListener(SQLite db) {
        this.db = db;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        this.db.addDeathToTable(event.getEntity().getDisplayName(), event.getEntity().getLastDamageCause().getEventName());
        Messenger.sendDeathMessage(event.getEntity().getDisplayName());
    }
}
