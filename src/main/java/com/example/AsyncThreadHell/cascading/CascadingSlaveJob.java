package com.example.AsyncThreadHell.cascading;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CascadingSlaveJob implements Runnable {

    final static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    private String id;

    public CascadingSlaveJob(String id) {
        this.id = id;
    }

    public void run() {
        logger.info("[INFO] " + Thread.currentThread().getName() + " starts async count job with id= " + id);
        try {
            Thread.sleep((int) Math.floor(Math.random() * 20000)); // 20s
        } catch (InterruptedException e) {
            // Todo logger info
        }
        logger.info("[INFO] " + Thread.currentThread().getName() + " finished async count job with id=  " + id);
    }
}