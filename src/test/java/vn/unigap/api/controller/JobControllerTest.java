package vn.unigap.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.dto.in.CreateJobRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;

import java.util.Arrays;
import java.util.Date;

//@WebMvcTest(JobController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getList() throws Exception {
        int page = 1;
        int size = 10;

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/jobs")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.page").value(page))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.pageSize").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.totalElements").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.totalPages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.data").isArray());
    }

    @Test
    public void testCreate() throws Exception {
        CreateJobRequest test = new CreateJobRequest(
                "Dummy Job Title",
                12345L,
                5,
                "This is a dummy job description.",
                Arrays.asList(1, 2, 3),
                Arrays.asList(101, 102, 103),
                50000,
                new Date()
        );

        // TEST SUCCESS CASE
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getOne() throws Exception {
        // TEST SUCCESS CASE
        Long sampleId = 4488937L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/jobs/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").exists());

        // TEST FAILED CASE
        String invalidId = "4488937L";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/jobs/{id}", invalidId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testUpdate() throws Exception {
        Long testId = 1L;

        UpdateJobRequest test = new UpdateJobRequest(
                1L,
                "Dummy Job Title",
                12345,
                "fsdfd",
                Arrays.asList(1, 2, 3),
                Arrays.asList(101, 102, 103),
                50000,
                new Date()
        );
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/jobs/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/jobs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
