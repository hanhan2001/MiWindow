package me.xiaoying.window.event;

public class Cancellable {
    private boolean canceled = false;

    boolean isCancelled() {
        return this.canceled;
    }

    void setCancelled(boolean bool) {
        this.canceled = bool;
    }
}