package app.durkin.erasure.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private String path;

    public ConfigManager(String path) {
        this.path = path;
    }

    public boolean doesConfigExist() {
        return new File(this.path).exists();
    }

    public void generateNewConfig() throws IOException {
        Map<String, Object> newConfig = new HashMap<>();
        newConfig.put("resetTime", 2);
        newConfig.put("deleteOnReset", true);
        newConfig.put("messageOnDeath", true);
        newConfig.put("maxDeaths", 1);
        setConfig(newConfig);
    }

    public int getResetTimeInMinutes() throws IOException {
        Map<String, Object> config = readConfig();
        return (int) config.getOrDefault("resetTime", -1);
    }

    public boolean getDeleteOnReset() throws IOException {
        Map<String, Object> config = readConfig();
        return (boolean) config.getOrDefault("deleteOnReset", null);
    }

    public boolean getMessageOnDeath() throws IOException {
        Map<String, Object> config = readConfig();
        return (boolean) config.getOrDefault("messageOnDeath", null);
    }

    public int getMaxNumberOfDeaths() throws IOException {
        Map<String, Object> config = readConfig();
        return (int) config.getOrDefault("maxDeaths", -1);
    }

    public void setResetTimeInMinutes(int time) throws IOException {
        Map<String, Object> config = readConfig();
        config.put("resetTime", time);
        setConfig(config);
    }

    public void setDeleteOnReset(boolean reset) throws IOException {
        Map<String, Object> config = readConfig();
        config.put("deleteOnReset", reset);
        setConfig(config);
    }

    public void setMessageOnDeath(boolean message) throws IOException {
        Map<String, Object> config = readConfig();
        config.put("messageOnDeath", message);
        setConfig(config);
    }

    public void setMaxNumberOfDeaths(int max) throws IOException {
        Map<String, Object> config = readConfig();
        config.put("maxDeaths", max);
        setConfig(config);
    }

    // config is read from disk every time access is needed
    private Map<String, Object> readConfig() throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(this.path);
        return yaml.load(inputStream);
    }

    // config is written to disk every time config is changed
    private void setConfig(Map<String, Object> newConfig) throws IOException {
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(this.path);
        yaml.dump(newConfig, writer);
    }
}
