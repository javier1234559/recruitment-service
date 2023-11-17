package vn.unigap.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.service.EmployerService;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
@AutoConfigureMockMvc
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployerService employerService; // Assuming you have an EmployerService

    @InjectMocks
    private EmployerController yourControllerClass; // The controller you are testing

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetListEmployer() {
        int page = 2;
        int size = 5;

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/employer")
                            .param("page", String.valueOf(page))
                            .param("size", String.valueOf(size))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(size));
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }


    }

    @Test
    public void testGetEmployerById(){
        int sampleId = 3093565 ;
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/employer/{id}", sampleId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3093565))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ba79721c98414bc050e66730326a92c9fd7f050b@sieu-viet.com"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Công ty TNHH Ứng Dụng Công Nghệ Số New Tech"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.provinceId").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.provinceName").value("Chua biet province name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(""));
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    @Test
    public void testCreateEmployer() {
        try {
//             Mock the behavior of the service to return a success response
            when(employerService.createEmployer(any())).thenReturn("New employer created successfully with id ");

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/employer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"John Doe\",\"email\":\"n128424@gmail.com\",\"provinceId\":999,\"description\":\"This is an example description.\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateEmployer() {
        int testId = 52;
        try {
            // Update the employer with the specific ID
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/employer/{id}", testId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"John Doe123\",\"provinceId\":132,\"description\":\"Da update\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Employer with ID: " + testId + " updated successfully."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteEmployer(){
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/employer/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Employer with ID: 1 not found."));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
