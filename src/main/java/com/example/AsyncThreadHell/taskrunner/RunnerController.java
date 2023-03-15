package com.example.AsyncThreadHell.taskrunner;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/taskrunner", produces= MediaType.APPLICATION_JSON_VALUE)
public class RunnerController {

    private static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    @Autowired
    RunnerService runnerService;

    @PostMapping("/new")
    public ResponseEntity<RunnerResponse> runnerStartNew(@RequestParam(value = "name", defaultValue = "Job") String name)  {
        logger.info("[INFO] Start enqueue new job " + name);
        runnerService.doAsyncCountJob(name);
        logger.info("[INFO] Finish enqueue new job " + name);
        RunnerResponse response = new RunnerResponse("Jobs enqueued.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/activecount")
    public ResponseEntity<RunnerResponse> runnerGetActiveCount() {
        logger.info("[INFO] runnerService has {} active jobs", runnerService.getActiveJobs());
        RunnerResponse response = new RunnerResponse(runnerService.getActiveJobs());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/queuesize")
    public ResponseEntity<RunnerResponse> runnerGetQueSize() {
        logger.info("[INFO] runnerService has {} jobs in the queue", runnerService.getQueueSize());
        RunnerResponse response = new RunnerResponse(runnerService.getQueueSize());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
