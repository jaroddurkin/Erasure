package app.durkin.erasure;

import app.durkin.erasure.commands.CommandHandler;
import app.durkin.erasure.config.ConfigManager;
import app.durkin.erasure.config.PropertyManager;
import app.durkin.erasure.db.SQLite;
import app.durkin.erasure.events.DeathListener;
import app.durkin.erasure.events.JoinListener;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Erasure extends JavaPlugin implements Listener {

    private SQLite db;
    private ConfigManager configManager;
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

        String yamlPath = this.getDataFolder().getAbsolutePath() + System.getProperty("file.separator") + "erasure.yaml";
        this.configManager = new ConfigManager(yamlPath);
        if (!configManager.doesConfigExist()) {
            try {
                configManager.generateNewConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.deathTracker = new DeathTracker();
        this.propertyManager = new PropertyManager(getPathToPropsFile());
        this.resetHandler = new ServerResetHandler(this.db, this.deathTracker, this.propertyManager, serverPath, this.configManager);

        try {
            if (!configManager.doesConfigExist()) {
                configManager.generateNewConfig();
            }
            if (configManager.getDeleteOnReset()) {
                resetHandler.removeOldWorldIfPresent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.resetHandler.addLatestWorldToTableIfNeeded();

        getServer().getPluginManager().registerEvents(new DeathListener(this.db, this.deathTracker, this.resetHandler, this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this.db), this);
        getCommand("erasure").setExecutor(new CommandHandler(this.db, this.deathTracker, this.resetHandler, this.configManager));
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
