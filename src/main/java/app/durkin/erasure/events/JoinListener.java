package app.durkin.erasure.events;

import app.durkin.erasure.db.SQLite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private SQLite db;

    public JoinListener(SQLite db) {
        this.db = db;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // each player is added to a DB row to track any stats for offline players
        if (this.db.getPlayerUUID(event.getPlayer().getName()) == null) {
            this.db.addPlayerToDB(event.getPlayer().getName(), event.getPlayer().getUniqueId().toString());
        }
    }
}
