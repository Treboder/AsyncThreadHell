package com.example.AsyncThreadHell.taskrunner;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component // ... required to inject the pool size from application properties
public class RunnerService {

    // ToDo: inject the pool size from application properties
    //@Value("${spring.task.execution.pool.max-size}")
    int poolSize=5;

    final static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    private ThreadPoolTaskExecutor taskExecutor = getAsyncExecutor();

    public ThreadPoolTaskExecutor getAsyncExecutor() {
        logger.info("[INFO] Initialize ThreadPoolTaskExecutor (RunnerService) with pool size = "+poolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("TaskRunner-");
        executor.setCorePoolSize(poolSize);     //default: 1
        executor.setMaxPoolSize(poolSize);      // default: Integer.MAX_VALUE
        //executor.setQueueCapacity(500);       // default: Integer.MAX_VALUE
        //executor.setKeepAliveSeconds(120);    // default: 60 seconds
        executor.initialize();
        return executor;
    }

    public void doAsyncCountJob(String id) {
        for(int i = 0; i < 25; i++)
            taskExecutor.execute(new RunnerTask(id + "." + i));
    }

    public int getActiveJobs() {
        return taskExecutor.getActiveCount();
    }

    public int getQueueSize() {
        return taskExecutor.getQueueSize();
    }



}