package app.durkin.erasure;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Erasure extends JavaPlugin implements Listener {

    private SQLite db;
    private DeathTracker deathTracker;
    private PropertyManager propertyManager;
    private ServerResetHandler resetHandler;

    @Override
    public void onEnable() {
        firstRunCreatePluginDir();
        this.db = new SQLite(this);
        this.db.load();

        // hacky way to get to server folder
        String pluginPath = this.getDataFolder().getParentFile().getAbsolutePath();
        String serverPath = new File(pluginPath).getParentFile().getAbsolutePath();

        this.deathTracker = new DeathTracker();
        this.propertyManager = new PropertyManager(getPathToPropsFile());
        this.resetHandler = new ServerResetHandler(this.db, this.deathTracker, this.propertyManager, serverPath);
        this.resetHandler.removeOldWorldIfPresent();
        this.resetHandler.addLatestWorldToTableIfNeeded();

        getServer().getPluginManager().registerEvents(new DeathListener(this.db, this.deathTracker), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("erasure").setExecutor(new CommandHandler(this.db, this.deathTracker));
        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.resetHandler.setNewWorldNameIfNeeded();
        HandlerList.unregisterAll((JavaPlugin) this);
        getLogger().info("Plugin disabled.");
    }

    private void firstRunCreatePluginDir() {
        File dir = new File(this.getDataFolder().getPath());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private String getPathToPropsFile() {
        // hacky way to get to server folder
        String pluginPath = this.getDataFolder().getParentFile().getAbsolutePath();
        File serverFolder = new File(pluginPath).getParentFile();

        if (!serverFolder.exists()) {
            return null;
        } else {
            return serverFolder.getAbsolutePath() + System.getProperty("file.separator") + "server.properties";
        }
    }
}
