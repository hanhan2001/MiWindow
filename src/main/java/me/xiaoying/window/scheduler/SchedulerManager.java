package me.xiaoying.window.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler
 */
public class SchedulerManager implements Scheduler {
    private final ScheduledExecutorService executors = Executors.newScheduledThreadPool(200);
    private final Map<Integer, Task> knownTask = new HashMap<>();
    private Thread thread;

    public SchedulerManager() {
        this.initialize();
    }

    public void initialize() {
        this.thread = new Thread(() -> {
            synchronized (this) {
                while (!this.thread.isInterrupted() && !Thread.currentThread().isInterrupted()) {
                    try {
                        wait(1);
                    } catch (InterruptedException e) {
                        break;
                    }
                    this.knownTask.forEach((id, task) -> {
                        if ((task.getType() == Task.TaskType.SYNC_RUN || task.getType() == Task.TaskType.ASYNC_RUN) && task.isFinish()) {
                            this.cancelTask(id);
                            return;
                        }

                        if (task.getType() == Task.TaskType.ASYNC_RUN || task.getType() == Task.TaskType.ASYNC_REPEAT)
                            return;

                        if (!task.ready())
                            return;

                        task.run();
                    });
                }
            }
        });
        this.thread.start();
    }

    @Override
    public void cancelTask(int task) {
        Task task1 = this.knownTask.get(task);
        if (task1 == null)
            return;

        if (task1.getType() == Task.TaskType.ASYNC_RUN || task1.getType() == Task.TaskType.ASYNC_REPEAT) {
            task1.getScheduledFuture().cancel(true);
            return;
        }

        this.knownTask.remove(task);
    }

    @Override
    public void runTask(Runnable runnable) {
        runnable.run();
    }

    @Override
    public int scheduleSyncDelayedTask(Runnable runnable) {
        Task task = new Task(Task.TaskType.SYNC_RUN, runnable);
        return this.setupTask(task);
    }

    @Override
    public int scheduleSyncDelayedTask(Runnable runnable, long delay) {
        Task task = new Task(Task.TaskType.SYNC_RUN, runnable);
        Date date = new Date();
        task.setRunTime(new Date(date.getTime() + delay));
        return this.setupTask(task);
    }

    @Override
    public int scheduleSyncRepeatingTask(Runnable runnable, long delay, long period) {
        Task task = new Task(Task.TaskType.SYNC_REPEAT, runnable, period);
        Date date = new Date();
        task.setRunTime(new Date(date.getTime() + delay));
        return this.setupTask(task);
    }

    @Override
    public int scheduleAsyncDelayedTask(Runnable runnable) {
        Task task = new Task(Task.TaskType.ASYNC_RUN, runnable);
        task.setScheduledFuture(this.executors.schedule(runnable, 0, TimeUnit.MILLISECONDS));
        return this.setupTask(task);
    }

    @Override
    public int scheduleAsyncDelayedTask(Runnable runnable, long delay) {
        Task task = new Task(Task.TaskType.ASYNC_REPEAT, runnable);
        Date date = new Date();
        task.setRunTime(new Date(date.getTime() + delay));
        // 20tick = 1s
        this.executors.schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
        return this.setupTask(task);
    }

    @Override
    public int scheduleAsyncRepeatingTask(Runnable runnable, long delay, long period) {
        Task task = new Task(Task.TaskType.ASYNC_REPEAT, runnable, period);
        Date date = new Date();
        task.setRunTime(new Date(date.getTime() + delay));
        // 20tick = 1s
        this.executors.schedule(() -> new Thread(runnable).start(), delay * 50, TimeUnit.MILLISECONDS);
        return this.setupTask(task);
    }

    private int setupTask(Task task) {
        synchronized (this) {
            if (this.knownTask.isEmpty()) {
                this.knownTask.put(0, task);
                return 0;
            }

            int up = 0;
            for (Integer i : this.knownTask.keySet()) {
                if (i - up <= 1)
                    continue;

                up = i;
                break;
            }
            int id;
            if (up == 0)
                id = this.knownTask.size();
            else
                id = up  + 1;
            this.knownTask.put(id, task);
            return id;
        }
    }

    public void stop() {
        this.thread.interrupt();
        this.knownTask.clear();
        this.executors.shutdown();
    }
}