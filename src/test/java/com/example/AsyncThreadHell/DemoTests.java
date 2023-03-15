package com.example.AsyncThreadHell;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoTests {

    // @AutoConfigureMockMvc creates MockMvc instance correctly, despite IntelliJs warning
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @Order(1)
    public void taskExecutorNew() throws Exception {
        String json = new ObjectMapper().writeValueAsString(null);
        mockMvc.perform(post("/demos/taskexecutor/new"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("Jobs enqueued.")))
        ;
    }

    @Test
    @Order(2)
    public void taskExecutorQueueSize() throws Exception {
        mockMvc.perform(get("/demos/taskexecutor/queuesize"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", greaterThan(0)))
        ;
    }

    @Test
    @Order(3)
    public void taskExecutorActiveCount() throws Exception {
        mockMvc.perform(get("/demos/taskexecutor/activecount"))
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", greaterThan(0)))
        ;
    }

    @Test
    @Order(4)
    public void taskRunnerNew() throws Exception {
        mockMvc.perform(post("/demos/taskrunner/new"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("Jobs enqueued.")))
        ;
    }

    @Test
    @Order(5)
    public void taskRunnerQueueSize() throws Exception {
        mockMvc.perform(get("/demos/taskrunner/queuesize"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", greaterThan(0)))
        ;
    }

    @Test
    @Order(6)
    public void taskRunnerActiveCount() throws Exception {
        mockMvc.perform(get("/demos/taskrunner/activecount"))
                //.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value", greaterThan(0)))
        ;
    }


}
