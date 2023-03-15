package com.example.AsyncThreadHell.cascading;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cascading")
public class CascadingController {

    private static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    @Autowired
    CascadingService cascadingService;

    @PostMapping(path = "/createMasterJob" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CascadingMasterJob> createMasterJobWithModelID(@RequestBody CascadingRequest request) {
        // enqueue the master job, log and return response entity
        Long runID = cascadingService.enqueueMasterJob(request);
        CascadingMasterJob masterJob = cascadingService.getMasterJob(runID);
        return new ResponseEntity<>(masterJob, HttpStatus.OK);
    }

    @GetMapping(path = "/getMasterJobByRunID", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CascadingMasterJob> getMasterJobByRunID(@RequestParam(value = "runID") Long runID) {
        CascadingMasterJob masterJob = cascadingService.getMasterJob(runID);
        if(masterJob != null) {
            logger.info("[INFO] Controller returns run with id= {} having {} active jobs and {} jobs in the queue", runID, masterJob.getRunningSubTasks(), masterJob.getQueuedSubTasks());
            return new ResponseEntity<>(masterJob, HttpStatus.OK);
        }
        else {
            logger.warn("[WARN] Controller triggered wit runID={} that does not exist", runID);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllMasterJobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CascadingMasterJob>> getAllMasterJobs() {
        List<CascadingMasterJob> masterJobList = cascadingService.getAllMasterJobs();
        logger.info("[INFO] Controller returns {} master jobs", masterJobList.stream().count());
        return new ResponseEntity<>(masterJobList, HttpStatus.OK);
    }

    @GetMapping(path = "/getRunningMasterJobs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CascadingMasterJob>> getRunningMasterJobs() {
        List<CascadingMasterJob> masterJobList = cascadingService.getRunningMasterJobs();
        logger.info("[INFO] Controller returns {} running master jobs", masterJobList.stream().count());
        return new ResponseEntity<>(masterJobList, HttpStatus.OK);
    }

}
