package app.durkin.erasure;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class ServerResetHandler {

    private SQLite db;
    private DeathTracker deathTracker;
    private PropertyManager propertyManager;
    private String path;

    public ServerResetHandler(SQLite db, DeathTracker deathTracker, PropertyManager propertyManager, String path) {
        this.db = db;
        this.deathTracker = deathTracker;
        this.propertyManager = propertyManager;
        this.path = path;
    }

    public void removeOldWorldIfPresent() {
        String worldName = this.propertyManager.getWorldName();
        String latestWorld = this.db.getLatestWorldName();
        if (latestWorld != null && !worldName.equals(latestWorld)) {
            findAndDeleteWorlds(latestWorld);
        }
    }

    public void addLatestWorldToTableIfNeeded() {
        String worldName = this.propertyManager.getWorldName();
        String latestWorld = this.db.getLatestWorldName();
        if (latestWorld == null || !worldName.equals(latestWorld)) {
            this.db.addWorldNameToTable(worldName);
        }
    }

    public void setNewWorldNameIfNeeded() {
        if (this.deathTracker.isServerResetting()) {
            try {
                this.propertyManager.setWorldName(worldNameGenerator());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void findAndDeleteWorlds(String latestWorld) {
        File directory = new File(this.path);
        String[] contents = directory.list();
        if (contents != null) {
            for (String file : contents) {
                if (file.contains(latestWorld)) {
                    File world = new File(this.path + System.getProperty("file.separator") + file);
                    try {
                        worldDeletion(world);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void worldDeletion(File world) throws IOException {
        if (world.isDirectory()) {
            File[] entries = world.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    worldDeletion(entry);
                }
            }
        }
        if (!world.delete()) {
            throw new IOException("Failed to delete " + world);
        }
    }

    private String worldNameGenerator() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
