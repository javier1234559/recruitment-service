package vn.unigap.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.dto.in.CreateEmployerRequest;
import vn.unigap.api.dto.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerRequest;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.dto.PageDtoOut;
import vn.unigap.api.service.employer.EmployerService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;

    @Test
    public void testGetListEmployer() throws Exception {
        int page = 2;
        int size = 2;

        List<EmployerResponse> mockList = Arrays.asList(
                new EmployerResponse(1L, "test@gmail.com", "TEst", 1, "Province 1", "Description 1"),
                new EmployerResponse(2L, "example2@gmail.com", "Example", 2, "Province 2", "Description 2"),
                new EmployerResponse(3L, "example3@gmail.com", "Example3", 3, "Province 3", "Description 3"),
                new EmployerResponse(4L, "example@4gmail.com", "Example4", 4, "Province 4", "Description 4")
        );

        PageDtoOut<EmployerResponse> pageDtoOut = PageDtoOut.from(page, size, (long) mockList.size(), mockList);
        PageDtoIn pageDtoIn = new PageDtoIn(page, size);
        when(employerService.getAll(pageDtoIn)).thenReturn(pageDtoOut);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/employers")
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
    public void testGetEmployerById() throws Exception {
        // TEST SUCCESS CASE
        // This will test /api/v1/employers/{id} will be get id and pass to employerService or not
        // and response as expected or not

        Long sampleId = 1L;
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        EmployerResponse test = new EmployerResponse(1L, "test@gmail.com", "TEst", 1, "Province 1", "Description 1");
        when(employerService.getOne(idCaptor.capture())).thenReturn(test);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employers/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").exists());
        assertEquals(sampleId, idCaptor.getValue());

        // TEST FAILED CASE
        // This will test /api/v1/employers/{id}  the id is passed is valid or not
        // and response as expected or not
        String invalidId = "1L";

        EmployerResponse testInvalid = new EmployerResponse(1L, "test@gmail.com", "TEst", 1, "Province 1", "Description 1");
        when(employerService.getOne(idCaptor.capture())).thenReturn(testInvalid);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employers/{id}", invalidId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    public void testCreateEmployer() throws Exception {
        CreateEmployerRequest employerDtoIn = new CreateEmployerRequest("test@gmail.com", "TEst", 1, "Description 1");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employerDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateEmployer() throws Exception {
        Long testId = 1L;

        UpdateEmployerRequest updateEmployerRequest = new UpdateEmployerRequest("Name", 123, "Description");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employers/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateEmployerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the updateEmployer method is called with the correct arguments
        verify(employerService).update(eq(testId), eq(updateEmployerRequest));
    }

    @Test
    public void testDeleteEmployer() throws Exception {
        Long id = 1L;
        doNothing().when(employerService).delete(id);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(employerService, times(1)).delete(id);
    }
}
