package com.example.notesapplication.integration;

import com.example.notesapplication.NotesApplication;
import com.example.notesapplication.model.NotesDTO;
import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.NotesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NotesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotesApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private NotesRepository notesRepository;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "john.doe@gmail.com", password = "YourPassword")
    public void testGetUserDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getUserDetails"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john.doe@gmail.com", password = "YourPassword")
    public void testAuth() throws Exception {
        UserDTO expected = new UserDTO("john.doe@gmail.com", "YourPassword");
        NotesDTO notes = new NotesDTO(expected, "TestNote");
        notesRepository.save(notes);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getUserDetails"))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString());

        assertThat(jsonNode.get("emailAddress").asText()).isEqualTo(expected.getEmailAddress());
        assertThat(jsonNode.get("password").asText()).isEqualTo(expected.getPassword());
    }

}
