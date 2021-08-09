package app.durkin.erasure.commands;

import app.durkin.erasure.config.ConfigManager;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ConfigCommandTest {

    CommandSender sender;
    ConfigManager configManager;

    @BeforeEach
    void setUp() throws IOException {
        this.sender = mock(CommandSender.class);
        doNothing().when(this.sender).sendMessage(anyString());
        when(this.sender.hasPermission(anyString())).thenReturn(true);

        this.configManager = mock(ConfigManager.class);
        doNothing().when(this.configManager).setDeleteOnReset(anyBoolean());
        doNothing().when(this.configManager).setResetTimeInMinutes(anyInt());
        when(this.configManager.getDeleteOnReset()).thenReturn(true);
        when(this.configManager.getResetTimeInMinutes()).thenReturn(2);
    }

    @Test
    void successful_get_first_prop() {
        String[] args = new String[]{"config", "get", "resetTime"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void successful_get_second_prop() {
        String[] args = new String[]{"config", "get", "deleteWorld"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void successful_get_case_insensitive() {
        String[] args = new String[]{"config", "gEt", "Deleteworld"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void successful_set_first_prop() {
        String[] args = new String[]{"config", "set", "resetTime", "2"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void successful_set_second_prop() {
        String[] args = new String[]{"config", "set", "deleteWorld", "false"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void successful_set_case_insensitive() {
        String[] args = new String[]{"config", "sEt", "Deleteworld", "False"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void get_too_many_args() {
        String[] args = new String[]{"config", "get", "resetTime", "2"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void get_too_few_args() {
        String[] args = new String[]{"config", "get"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void get_invalid_property() {
        String[] args = new String[]{"config", "get", "prop"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void set_too_many_args() {
        String[] args = new String[]{"config", "set", "resetTime", "2", "true"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void set_too_few_args() {
        String[] args = new String[]{"config", "set", "resetTime"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void set_invalid_property() {
        String[] args = new String[]{"config", "set", "prop", "true"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void invalid_config_keyword() {
        String[] args = new String[]{"config", "remove", "resetTime"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void no_keyword_after_config() {
        String[] args = new String[]{"config"};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void no_args() {
        String[] args = new String[]{};
        ConfigCommand commandHandler = new ConfigCommand(this.sender, args, this.configManager);
        assertFalse(commandHandler.handleCommand());
    }
}
