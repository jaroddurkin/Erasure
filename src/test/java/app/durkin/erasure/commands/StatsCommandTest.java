package app.durkin.erasure.commands;

import app.durkin.erasure.db.SQLite;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class StatsCommandTest {

    SQLite db;
    CommandSender sender;

    @BeforeEach
    void setUp() {
        this.db = mock(SQLite.class);
        when(this.db.getNumberOfDeathsForPlayer(any())).thenReturn(5);

        this.sender = mock(CommandSender.class);
        when(this.sender.getName()).thenReturn("Test");
        doNothing().when(this.sender).sendMessage(anyString());
    }

    @Test
    void self_stats() {
        String[] args = new String[]{"stats"};
        StatsCommand commandHandler = new StatsCommand(this.sender, args, this.db);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void other_user_stats() {
        String[] args = new String[]{"stats", "User2"};
        StatsCommand commandHandler = new StatsCommand(this.sender, args, this.db);
        assertTrue(commandHandler.handleCommand());
    }

    @Test
    void too_many_arguments() {
        String[] args = new String[]{"stats", "User2", "User3"};
        StatsCommand commandHandler = new StatsCommand(this.sender, args, this.db);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void too_few_arguments() {
        String[] args = new String[]{};
        StatsCommand commandHandler = new StatsCommand(this.sender, args, this.db);
        assertFalse(commandHandler.handleCommand());
    }
}
