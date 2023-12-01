package vn.unigap.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.EmployerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

@SpringBootTest()
@AutoConfigureMockMvc
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetListEmployer() throws Exception {
        int page = 2;
        int size = 2;

        List<EmployerDtoOut> mockList = Arrays.asList(
                new EmployerDtoOut(1L, "test@gmail.com", "TEst", 1, "Province 1", "Description 1"),
                new EmployerDtoOut(2L, "example2@gmail.com", "Example", 2, "Province 2", "Description 2"),
                new EmployerDtoOut(3L, "example3@gmail.com", "Example3", 3, "Province 3", "Description 3"),
                new EmployerDtoOut(4L, "example@4gmail.com", "Example4", 4, "Province 4", "Description 4")
        );

        PageDtoOut<EmployerDtoOut> pageDtoOut = PageDtoOut.from(page, size, (long) mockList.size(), mockList);
        PageDtoIn pageDtoIn = new PageDtoIn(page, size);
        when(employerService.getListEmployer(pageDtoIn)).thenReturn(pageDtoOut);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employer")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.page").value(page))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.pageSize").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.totalElements").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.totalPages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.data.length()").value(size));
    }

    @Test
    public void testGetEmployerById() throws Exception {
        int sampleId = 3094094;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employer/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").exists());

    }

    @Test
    public void testCreateEmployer() throws Exception {
        EmployerDtoIn employerDtoIn = new EmployerDtoIn("test@gmail.com", "TEst", 1, "Description 1");
        doNothing().when(employerService).createEmployer(employerDtoIn);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateEmployer() throws Exception {
        Long testId = 1L;

        UpdateEmployerDtoIn updateEmployerDtoIn = new UpdateEmployerDtoIn("Name", 123, "Description");
        doNothing().when(employerService).updateEmployer(1L, updateEmployerDtoIn);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employer/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteEmployer() throws Exception {
        Long id = 1L ;
        doNothing().when(employerService).deleteEmployer(id);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(employerService, times(1)).deleteEmployer(id);
    }
}
