package com.example.AsyncThreadHell.jobrunr;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/jobrunr")
public class JobrunrController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    @Autowired // or @Inject
    private JobScheduler jobScheduler;

    @Autowired // or @Inject
    private SampleJobService sampleJobService;

    @PostMapping("/runJob")
    public String runJob(@RequestParam(value = "name", defaultValue = "Hello World") String name) {
        jobScheduler.enqueue(() -> sampleJobService.execute(name));
        return "Job is enqueued.";
    }

    @PostMapping("/scheduleJob")
    public String scheduleJob(@RequestParam(value = "name", defaultValue = "Hello World") String name,
            @RequestParam(value = "when", defaultValue = "PT3H") String when) {
        jobScheduler.schedule(Instant.now().plus(Duration.parse(when)), () -> sampleJobService.execute(name));
        return "Job is scheduled.";
    }

}
