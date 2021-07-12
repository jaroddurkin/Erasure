package app.durkin.erasure;

public class DeathTracker {

    private boolean serverReset;

    public DeathTracker() {
        this.serverReset = false;
    }

    public boolean isServerResetting() {
        return this.serverReset;
    }

    public void toggleServerReset() {
        if (this.serverReset) {
            this.serverReset = false;
        } else {
            this.serverReset = true;
        }
    }
}
