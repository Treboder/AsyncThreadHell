package com.example.AsyncThreadHell.taskexecutor;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/taskexecutor", produces= MediaType.APPLICATION_JSON_VALUE)
public class ExecutorController {

    private static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    @Autowired
    ExecutorService executorService;

    @PostMapping("/new")
    public ResponseEntity<ExecutorResponse> serviceStartNew(@RequestParam(value = "name", defaultValue = "Job") String name) {
        logger.info("[INFO] Start enqueue new job " + name);
        for(int i=0; i<25; i++)
            executorService.doAsyncCountJob(name + "." + i);
        logger.info("[INFO] Finish enqueue new job " + name);
        ExecutorResponse response = new ExecutorResponse("Jobs enqueued.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/activecount")
    public ResponseEntity<ExecutorResponse> serviceGetActiveCount() {
        logger.info("[INFO] executorService has {} active jobs", executorService.getActiveJobs());
        ExecutorResponse response = new ExecutorResponse(executorService.getActiveJobs());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/queuesize")
    public ResponseEntity<ExecutorResponse> serviceGetQueSize() {
        logger.info("[INFO] executorService has {} jobs in the queue", executorService.getQueueSize());
        ExecutorResponse response = new ExecutorResponse(executorService.getQueueSize());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
