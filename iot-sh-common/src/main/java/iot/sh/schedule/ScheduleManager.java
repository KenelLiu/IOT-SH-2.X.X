package iot.sh.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Kenel Liu
 */
@Slf4j
public class ScheduleManager {
    private static final ScheduleManager bootStrap=new ScheduleManager();
    ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(5);
    ExecutorService executor=Executors.newCachedThreadPool();

    public static  ScheduleManager getInstance(){
        return bootStrap;
    }

    private ScheduleManager(){
    }

    public void addExecutor(Runnable task){
        executor.submit(task);
    }

    <T> Future<T> addExecutor(Runnable task, T result){
        return executor.submit(task,result);
    }

    public void addSchedule(Runnable task){
        scheduleExecutor.submit(task);
    }

    public void addSchedule(Runnable task,long delay, TimeUnit unit){
        scheduleExecutor.schedule(task,delay,unit);
    }

    public void addSchedule(Runnable task,long delay,long period,TimeUnit unit){
        scheduleExecutor.scheduleAtFixedRate(task, delay, period, unit);
    }


}
