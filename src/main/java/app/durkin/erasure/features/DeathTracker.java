package app.durkin.erasure.features;

public class DeathTracker {

    private boolean serverReset;
    private int taskId;

    public DeathTracker() {
        this.serverReset = false;
        this.taskId = -1;
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

    public int getTaskId() {
        return this.taskId;
    }

    public void setResetTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setDefaultResetTaskId() {
        this.taskId = -1;
    }
}
