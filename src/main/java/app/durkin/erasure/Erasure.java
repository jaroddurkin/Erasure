package app.durkin.erasure;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Erasure extends JavaPlugin implements Listener {

    private SQLite db;

    @Override
    public void onEnable() {
        firstRunCreatePluginDir();
        this.db = new SQLite(this);
        this.db.load();
        getServer().getPluginManager().registerEvents(new DeathListener(this.db), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("erasure").setExecutor(new CommandHandler(this.db));
        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((JavaPlugin) this);
        getLogger().info("Plugin disabled.");
    }

    private void firstRunCreatePluginDir() {
        File dir = new File(this.getDataFolder().getPath());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
