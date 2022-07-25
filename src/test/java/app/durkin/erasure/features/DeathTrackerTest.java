package app.durkin.erasure.features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeathTrackerTest {

    DeathTracker deathTracker;

    @BeforeEach
    void setUp() {
        this.deathTracker = new DeathTracker(5, 0);
    }

    @Test
    void server_not_resetting_instantly() {
        assertFalse(this.deathTracker.isServerResetting());
    }

    @Test
    void register_one_death_no_reset() {
        assertFalse(this.deathTracker.registerDeath());
    }

    @Test
    void register_five_deaths_reset() {
        for (int i = 0; i < 4; i++) {
            this.deathTracker.registerDeath();
        }
        assertTrue(this.deathTracker.registerDeath());
    }

    @Test
    void toggle_flips_successfully() {
        assertFalse(this.deathTracker.isServerResetting());
        this.deathTracker.toggleServerReset();
        assertTrue(this.deathTracker.isServerResetting());
    }

    @Test
    void get_initial_taskId() {
        assertEquals(-1, this.deathTracker.getTaskId());
    }

    @Test
    void set_taskId_success() {
        this.deathTracker.setResetTaskId(10);
        assertEquals(10, this.deathTracker.getTaskId());
    }

    @Test
    void default_taskId() {
        this.deathTracker.setResetTaskId(10);
        this.deathTracker.setDefaultResetTaskId();
        assertEquals(-1, this.deathTracker.getTaskId());
    }
}
