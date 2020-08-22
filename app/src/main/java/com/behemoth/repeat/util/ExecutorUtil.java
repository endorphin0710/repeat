package com.behemoth.repeat.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {

    private static volatile ExecutorUtil instance;
    private ThreadPoolExecutor executor;

    private ExecutorUtil(){
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        executor = new ThreadPoolExecutor(
                2,
                4,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2)
        );
    }

    public static ExecutorUtil getInstance(){
        if(instance == null){
            synchronized (ExecutorUtil.class){
                if(instance == null) instance = new ExecutorUtil();
            }
        }
        return instance;
    }

    public void execute(Runnable r){
        executor.execute(r);
    }

}
