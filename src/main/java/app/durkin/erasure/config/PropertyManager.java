package app.durkin.erasure.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private final String path;

    public PropertyManager(String path) {
        this.path = path;
    }

    public String getWorldName() {
        Properties props = readPropertiesFile();
        if (props == null) {
            return "";
        }
        return props.getProperty("level-name");
    }

    public void setWorldName(String worldName) throws IOException {
        Properties props = readPropertiesFile();
        if (props == null) {
            throw new IOException();
        }
        // set new world name to generate world and act as server reset
        props.setProperty("level-name", worldName);

        FileOutputStream outputStream = new FileOutputStream(this.path);
        props.store(outputStream, null);
    }

    // reads the minecraft server.properties file
    private Properties readPropertiesFile() {
        FileInputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = new FileInputStream(this.path);
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
