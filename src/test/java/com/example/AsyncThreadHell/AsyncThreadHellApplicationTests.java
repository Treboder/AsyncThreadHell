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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AsyncThreadHellApplicationTests {

	// @AutoConfigureMockMvc creates MockMvc instance correctly, despite IntelliJs warning
	@Autowired
	protected MockMvc mockMvc;

	@Value("${com.example.AsyncThreadHell.total-sub-tasks}")
	private int defaultTotalSubTasks;

	@Value("${spring.task.execution.pool.max-size}")
	private int defaultMaxConcurrentThreads;

	private String testJobDescription = "my job description";
	private int testTotalSubtasks = 20;
	private int testMaxThreads = 10;

	@Test
	@Order(1)
	public void taskExecutorNew() throws Exception {
		String json = new ObjectMapper().writeValueAsString(null);
		mockMvc.perform(post("/taskexecutor/new"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("Jobs enqueued.")))
		;
	}

	@Test
	@Order(2)
	public void taskExecutorQueueSize() throws Exception {
		mockMvc.perform(get("/taskexecutor/queuesize"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.value", greaterThan(0)))
		;
	}

	@Test
	@Order(3)
	public void taskExecutorActiveCount() throws Exception {
		mockMvc.perform(get("/taskexecutor/activecount"))
				//.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.value", greaterThan(0)))
		;
	}

	@Test
	@Order(4)
	public void taskRunnerNew() throws Exception {
		mockMvc.perform(post("/taskrunner/new"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("Jobs enqueued.")))
		;
	}

	@Test
	@Order(5)
	public void taskRunnerQueueSize() throws Exception {
		mockMvc.perform(get("/taskrunner/queuesize"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.value", greaterThan(0)))
		;
	}

	@Test
	@Order(6)
	public void taskRunnerActiveCount() throws Exception {
		mockMvc.perform(get("/taskrunner/activecount"))
				//.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.value", greaterThan(0)))
		;
	}

	@Test
	@Order(7)
	public void createMasterJobWithDefaults() throws Exception {
		CascadingRequest request = new CascadingRequest();
		String json = new ObjectMapper().writeValueAsString(request);
		mockMvc.perform(post("/cascading/createMasterJob").contentType(MediaType.APPLICATION_JSON).content(json))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.runID", is(1)))
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
	@Order(8)
	public void getFirstMasterJobByRunID() throws Exception {
		mockMvc.perform(get("/cascading/getMasterJobByRunID").param("runID", "1"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.runID", is(1)))
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
	@Order(9)
	public void getAllMasterJobsAndCheckTheFirst() throws Exception {
		mockMvc.perform(get("/cascading/getAllMasterJobs"))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].runID", is(1)))
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


}
