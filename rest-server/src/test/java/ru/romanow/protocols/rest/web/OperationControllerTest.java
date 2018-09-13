package ru.romanow.protocols.rest.web;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.romanow.protocols.api.model.TestObjectRequest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class OperationControllerTest {
    private static final Integer CODE = 100;
    private static final String NAME = "TEST";

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    public void testProcessRequest() throws Exception {
        final Integer id = 101;
        final String searchString = randomAlphabetic(3);
        TestObjectRequest request =
                new TestObjectRequest(id, searchString);

        mockMvc.perform(post("/api/op/process")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code").value(CODE))
                .andExpect(jsonPath("$.data").value(NAME));
    }

    @Test
    public void testProcessRequestFail() throws Exception {
        final Integer id = 1;
        final String searchString = randomAlphabetic(3);
        TestObjectRequest request =
                new TestObjectRequest(id, searchString);

        mockMvc.perform(post("/api/op/process")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isIAmATeapot())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value("Id '1' too low"));
    }
}
