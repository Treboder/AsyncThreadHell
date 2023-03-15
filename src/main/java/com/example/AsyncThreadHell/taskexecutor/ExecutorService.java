package com.example.AsyncThreadHell.taskexecutor;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class ExecutorService {

    final static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    @Autowired
    ThreadPoolTaskExecutor taskExecutor; // autowiring works despite warning

    @Async
    public void doAsyncCountJob(String id) {
        logger.info("[INFO] " + Thread.currentThread().getName() + " starts async count job " + id);
        try {
            Thread.sleep((int) Math.floor(Math.random() * 20000)); // 20s
        } catch (InterruptedException e) {
            logger.info("[INFO] Thread.Sleep interrupt exception has ben catched for " + id);
        }
        logger.info("[INFO] " + Thread.currentThread().getName() + " finished async count job " + id);
    }

    public int getActiveJobs() {
        return taskExecutor.getActiveCount();
    }

    public int getQueueSize() {
        return taskExecutor.getQueueSize();
    }


}