package app.durkin.erasure.commands;

import app.durkin.erasure.events.Messenger;
import app.durkin.erasure.features.DeathTracker;
import app.durkin.erasure.features.ServerResetHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CancelCommandTest {

    CommandSender sender;
    DeathTracker deathTracker;
    ServerResetHandler resetHandler;
    String[] args = new String[]{"cancel"};
    MockedStatic<Messenger> MockMessenger;

    CancelCommand commandHandler;

    @BeforeEach
    void setUp() {
        this.sender = mock(CommandSender.class);
        when(this.sender.getName()).thenReturn("Test");

        this.deathTracker = mock(DeathTracker.class);
        doNothing().when(this.deathTracker).toggleServerReset();
        when(this.deathTracker.getTaskId()).thenReturn(1);

        this.resetHandler = mock(ServerResetHandler.class);
        doNothing().when(this.resetHandler).cancelRestartTaskIfExists(anyInt());

        commandHandler = new CancelCommand(this.sender, args, this.deathTracker, this.resetHandler);
    }

    @Test
    void no_permission_for_reset() {
        when(this.sender.hasPermission(anyString())).thenReturn(false);
        when(this.deathTracker.isServerResetting()).thenReturn(true);
        assertFalse(commandHandler.handleCommand());
    }

    @Test
    void server_is_not_resetting() {
        when(this.sender.hasPermission(anyString())).thenReturn(true);
        when(this.deathTracker.isServerResetting()).thenReturn(false);
        assertFalse(commandHandler.handleCommand());
    }
}
