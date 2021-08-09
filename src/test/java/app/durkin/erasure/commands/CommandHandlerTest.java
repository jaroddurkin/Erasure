package app.durkin.erasure.commands;

import app.durkin.erasure.config.ConfigManager;
import app.durkin.erasure.db.SQLite;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class CommandHandlerTest {

    CommandSender sender;
    org.bukkit.command.Command command;
    String s = "command";
    SQLite db;
    DeathTracker deathTracker;
    ServerResetHandler resetHandler;
    ConfigManager configManager;

    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        this.sender = mock(CommandSender.class);
        this.command = mock(org.bukkit.command.Command.class);
        this.db = mock(SQLite.class);
        this.deathTracker = mock(DeathTracker.class);
        this.resetHandler = mock(ServerResetHandler.class);
        this.configManager = mock(ConfigManager.class);

        this.commandHandler = new CommandHandler(this.db, this.deathTracker, this.resetHandler, this.configManager);
    }

    @Test
    void no_subcommand() {
        String[] args = new String[]{};
        assertFalse(commandHandler.onCommand(this.sender, this.command, this.s, args));
    }

    @Test
    void unknown_subcommand() {
        String[] args = new String[]{"random"};
        assertFalse(commandHandler.onCommand(this.sender, this.command, this.s, args));
    }
}
