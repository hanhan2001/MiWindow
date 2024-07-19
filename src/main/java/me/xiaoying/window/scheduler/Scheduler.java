package me.xiaoying.window.scheduler;

public interface Scheduler {
    void cancelTask(int task);

    void runTask(Runnable runnable);

    int scheduleSyncDelayedTask(Runnable runnable);
    int scheduleSyncDelayedTask(Runnable runnable, long delay);
    int scheduleSyncRepeatingTask(Runnable runnable, long delay, long period);

    int scheduleAsyncDelayedTask(Runnable runnable);
    int scheduleAsyncDelayedTask(Runnable runnable, long delay);
    int scheduleAsyncRepeatingTask(Runnable runnable, long delay, long period);
}