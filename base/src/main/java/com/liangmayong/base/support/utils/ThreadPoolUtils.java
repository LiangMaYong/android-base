package com.liangmayong.base.support.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by LiangMaYong on 2016/11/8.
 */
public final class ThreadPoolUtils {

    private ThreadPoolUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public enum Type {
        ScheduledThread,
        FixedThread,
        CachedThread,
        SingleThread,
    }

    private ExecutorService exec;
    private ScheduledExecutorService scheduleExec;

    /**
     * @param type         type
     * @param corePoolSize corePoolSize
     */
    public ThreadPoolUtils(Type type, int corePoolSize) {
        scheduleExec = Executors.newScheduledThreadPool(corePoolSize);
        switch (type) {
            case FixedThread:
                exec = Executors.newFixedThreadPool(corePoolSize);
                break;
            case SingleThread:
                exec = Executors.newSingleThreadExecutor();
                break;
            case CachedThread:
                exec = Executors.newCachedThreadPool();
                break;
            default:
                exec = scheduleExec;
                break;
        }
    }

    public void execute(Runnable command) {
        exec.execute(command);
    }

    public void execute(List<Runnable> commands) {
        for (Runnable command : commands) {
            exec.execute(command);
        }
    }

    public void shutDown() {
        exec.shutdown();
    }

    public List<Runnable> shutDownNow() {
        return exec.shutdownNow();
    }

    public boolean isShutDown() {
        return exec.isShutdown();
    }

    public boolean isTerminated() {
        return exec.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return exec.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return exec.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return exec.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return exec.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws
            InterruptedException {
        return exec.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return exec.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws
            InterruptedException, ExecutionException, TimeoutException {
        return exec.invokeAny(tasks, timeout, unit);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return scheduleExec.schedule(command, delay, unit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return scheduleExec.schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return scheduleExec.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return scheduleExec.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}
