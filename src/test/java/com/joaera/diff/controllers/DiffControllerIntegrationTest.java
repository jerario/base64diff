package com.joaera.diff.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaera.diff.constants.Constants;
import com.joaera.diff.models.Content;
import com.joaera.diff.models.Document;
import com.joaera.diff.services.DocumentRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DiffControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.repository.deleteAll();
    }

    @Test
    public void insertDocuments() throws Exception {
        leftDocument("012345");
        rightDocument("000000");
    }

    @Test
    public void differentDocuments() throws Exception {
        this.save("0123456789", "0923459799");
        String uri = "/v1/diff/1/";
        String message = Constants.DIFF_AND_SAME_LENGTH;
        int length = 10;
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.difference.length").value(length))
                .andExpect(jsonPath("$.difference.offsets").value(Matchers.containsInAnyOrder(1, 6, 8)));
    }

    @Test
    public void differentLengths() throws Exception {
        this.save("0123456789", "0923459799456");
        this.get("/v1/diff/1/", Constants.DIFF_LENGTH);
    }

    @Test
    public void equals() throws Exception {
        this.save("0123456789", "0123456789");
        this.get("/v1/diff/1/", Constants.EQUALS);
    }

    private void rightDocument(String data) throws Exception {
        this.post("/v1/diff/1/right", data);

        Optional<Document> savedDocument = repository.findById("1");
        Document document = savedDocument.get();

        assertEquals("The id is incorrect", "1", document.getId());
        assertEquals("Data is incorrect", data, document.getRight());
    }

    private void leftDocument(String data) throws Exception {
        this.post("/v1/diff/1/left", data);

        Optional<Document> savedDocument = repository.findById("1");
        Document document = savedDocument.get();

        assertEquals("The id is incorrect", "1", document.getId());
        assertEquals("Data is incorrect", data, document.getLeft());
    }

    private void save(String left, String right) {
        Document document = new Document("1", left, right);
        this.repository.save(document);
    }

    private void get(String uri, String message) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.difference").value(Matchers.nullValue()));
    }

    private void post(String uri, String data) throws Exception {
        Content content = new Content(data);
        mvc.perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content)))
                .andExpect(status().isCreated())
                .andReturn();
    }

}
