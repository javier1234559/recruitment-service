package vn.unigap.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/
//https://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit
//https://www.baeldung.com/guide-to-jayway-jsonpath
//https://goessner.net/articles/JsonPath/
//https://viblo.asia/p/thuc-hien-viet-unit-test-spring-mvc-rest-service-Eb85omY0Z2G
//https://site.mockito.org/

//https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4

//https://stacktobasics.com/mockito-cheatsheet
//https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
//https://lzone.de/cheat-sheet/JSONPath

/**
 * Cac van de can tim hieu de testing
 * 1. Tim hieu ve khai niem testing , o day la unit test
 * 2. Tim hieu ve cu phap cua MockMvc va Mockito
 * 3. TIm hieu ve cu phap cua jsonpath thong qua cheatsheet json-path
 * 3.
 */

@SpringBootTest()
@AutoConfigureMockMvc
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployerService employerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmployerById() {
        int sampleId = 3094094;
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/employer/{id}", sampleId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateEmployer() {
        try {
            when(employerService.createEmployer(any())).thenReturn("New employer created successfully with id ");

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/employer")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateEmployer() {
        int testId = 52;
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/employer/{id}", testId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Employer with ID: " + testId + " updated successfully."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteEmployer() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/employer/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
