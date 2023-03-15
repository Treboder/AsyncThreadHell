package com.example.AsyncThreadHell.taskrunner;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerTask implements Runnable {

    final static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    private String id;

    public RunnerTask(String id) {
        this.id = id;
    }

    public void run() {
        logger.info("[INFO] " + Thread.currentThread().getName() + " starts async count job " + id);
        try {
            Thread.sleep((int) Math.floor(Math.random() * 20000)); // 20s
        } catch (InterruptedException e) {
            logger.info("[INFO] Thread.Sleep interrupt exception has ben catched for " + id);
        }
        logger.info("[INFO] " + Thread.currentThread().getName() + " finished async count job " + id);
    }
}

