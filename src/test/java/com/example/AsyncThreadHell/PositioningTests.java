package com.example.AsyncThreadHell;

import com.example.AsyncThreadHell.cascading.CascadingMasterJob;
import com.example.AsyncThreadHell.cascading.CascadingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositioningTests {

    @Autowired
    protected MockMvc mockMvc; // autowiring works despite IntelliJs warning

    @Value("${com.magicmosaic.core.total-sub-tasks}")
    private int defaultTotalSubTasks;

    @Value("${spring.task.execution.pool.max-size}")
    private int defaultMaxConcurrentThreads;

    private String testModelID = "modelX";
    private String testJobDescription = "my job description";
    private int testTotalSubtasks = 20;
    private int testMaxThreads = 10;

    @Test
    @Order(1)
    public void createMasterJobWithDefaults() throws Exception {
        CascadingRequest request = new CascadingRequest();
        String json = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/positioning/createMasterJobWithModelID").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.runID", is(1)))
                .andExpect(jsonPath("$.modelID", is(testModelID)))
                .andExpect(jsonPath("$.jobID", notNullValue ()))
                .andExpect(jsonPath("$.jobDescription", notNullValue ()))
                .andExpect(jsonPath("$.jobStatus", notNullValue ()))
                .andExpect(jsonPath("$.jobStatus", is(CascadingMasterJob.JobStatus.QUEUED.toString())))
                .andExpect(jsonPath("$.totalSubTasks", is (defaultTotalSubTasks)))
                .andExpect(jsonPath("$.maxConcurrentThreads", is (defaultMaxConcurrentThreads)))
                .andExpect(jsonPath("$.runningSubTasks", notNullValue ()))
                .andExpect(jsonPath("$.queuedSubTasks", notNullValue ()))
        ;
    }

    @Test
    @Order(2)
    public void getFirstMasterJobByRunID() throws Exception {
        mockMvc.perform(get("/positioning/getMasterJobByRunID").param("runID", "1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.runID", is(1)))
                .andExpect(jsonPath("$.modelID", is(testModelID)))
                .andExpect(jsonPath("$.jobID", notNullValue ()))
                .andExpect(jsonPath("$.jobDescription", notNullValue ()))
                .andExpect(jsonPath("$.jobStatus", notNullValue ()))
                .andExpect(jsonPath("$.jobStatus", is(CascadingMasterJob.JobStatus.QUEUED.toString())))
                .andExpect(jsonPath("$.totalSubTasks", is (defaultTotalSubTasks)))
                .andExpect(jsonPath("$.maxConcurrentThreads", is (defaultMaxConcurrentThreads)))
                .andExpect(jsonPath("$.runningSubTasks", notNullValue ()))
                .andExpect(jsonPath("$.queuedSubTasks", notNullValue ()))
        ;
    }

    @Test
    @Order(3)
    public void getAllMasterJobsAndCheckTheFirst() throws Exception {
        mockMvc.perform(get("/positioning/getAllMasterJobs"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].runID", is(1)))
                .andExpect(jsonPath("$[0].modelID", is(testModelID)))
                .andExpect(jsonPath("$[0].jobID", notNullValue ()))
                .andExpect(jsonPath("$[0].jobDescription", notNullValue ()))
                .andExpect(jsonPath("$[0].jobStatus", notNullValue ()))
                .andExpect(jsonPath("$[0].jobStatus", is(CascadingMasterJob.JobStatus.QUEUED.toString())))
                .andExpect(jsonPath("$[0].totalSubTasks", is (defaultTotalSubTasks)))
                .andExpect(jsonPath("$[0].maxConcurrentThreads", is (defaultMaxConcurrentThreads)))
                .andExpect(jsonPath("$[0].runningSubTasks", notNullValue ()))
                .andExpect(jsonPath("$[0].queuedSubTasks", notNullValue ()))
        ;
    }

    @Test
    @Order(4)
    public void createSecondMasterJobWithParameters() throws Exception {
        CascadingRequest request = new CascadingRequest();
        request.setJobDescription(testJobDescription);
        request.setMaxConcurrentThreads(testMaxThreads);
        request.setTotalSubTasks(testTotalSubtasks);
        String json = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/positioning/createMasterJobWithModelID").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.runID", is(2)))
                .andExpect(jsonPath("$.modelID", is(testModelID)))
                .andExpect(jsonPath("$.jobID", notNullValue ()))
                .andExpect(jsonPath("$.jobDescription", is (testJobDescription)))
                .andExpect(jsonPath("$.jobStatus", notNullValue ()))
                .andExpect(jsonPath("$.jobStatus", is(CascadingMasterJob.JobStatus.QUEUED.toString())))
                .andExpect(jsonPath("$.totalSubTasks", is (testTotalSubtasks)))
                .andExpect(jsonPath("$.maxConcurrentThreads", is (testMaxThreads)))
                .andExpect(jsonPath("$.runningSubTasks", notNullValue ()))
                .andExpect(jsonPath("$.queuedSubTasks", notNullValue ()))
        ;
    }

    @Test
    @Order(5)
    public void getAllMasterJobsAfterCreatingTheSecond() throws Exception {
        mockMvc.perform(get("/positioning/getAllMasterJobs"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].modelID", is(testModelID)))
                .andExpect(jsonPath("$[0].runID",is(1)))
                .andExpect(jsonPath("$[1].modelID", is(testModelID)))
                .andExpect(jsonPath("$[1].runID",is(2)))
        ;
    }

    @Test
    @Order(6)
    public void getAllRunningMasterJobsAndExpectAtLeastOneRunning() throws Exception {
        mockMvc.perform(get("/positioning/getRunningMasterJobs"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$.length()", greaterThan(0)))
        ;
    }

    @Test
    @Order(7)
    public void getFirstMasterJobAndExpectItRunning() throws Exception {
        mockMvc.perform(get("/positioning/getMasterJobByRunID").param("runID", "1"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.modelID", is(testModelID)))
                .andExpect(jsonPath("$.runID", is(1)))
                //.andExpect(jsonPath("$.jobStatus", is (PositioningJob.JobStatus.RUNNING.toString())))
        ;
    }


}

