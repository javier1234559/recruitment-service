package vn.unigap.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.dto.in.AuthLoginRequest;
import vn.unigap.api.dto.in.CreateEmployerRequest;
import vn.unigap.api.dto.in.UpdateEmployerRequest;
import vn.unigap.api.dto.in.UpdateJobRequest;
import vn.unigap.api.dto.out.AuthLoginResponse;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.service.employer.EmployerService;
import vn.unigap.common.CustomResponse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String accessToken;

    @BeforeEach
    void getAccessToken() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        var uri = "/api/v1/auth/login";

        AuthLoginRequest loginRequest = new AuthLoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("user");

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(loginRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> {
                    var response = objectMapper.readValue(
                            result.getResponse().getContentAsString()
                            , new TypeReference<CustomResponse<AuthLoginResponse>>() {
                            }
                    );
                    accessToken = response.getObject().getAccessToken();
                });
    }

    @Test
    public void getList() throws Exception {
        // Arrange-Act-Assert pattern
        // Arrange
        int page = 1;
        int size = 10;

        // Act
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/employers")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(request);

        // Assert
        response.andDo(MockMvcResultHandlers.print())
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
    public void getOne() throws Exception {
        // Arrange-Act-Assert pattern
        // Arrange
        Long id = 3093562L;

        // Act
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/employers/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilder);

        // Assert
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").exists());
    }

    @Test
    public void testCreate() throws Exception {
        CreateEmployerRequest testObject = new CreateEmployerRequest("test@gmail.com", "TEst", 1, "Description 1");

        // Act
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/employers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testObject))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(request);

        // Assert
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange-Act-Assert pattern
        // Arrange
        Long id = 3093562L;
        UpdateEmployerRequest testObject = new UpdateEmployerRequest("Name", 123, "Description");
        // Act
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/employers/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testObject));
        ResultActions response = mockMvc.perform(requestBuilder);
        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange-Act-Assert pattern
        Long id = 3093562L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employers/{id}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
