package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
        this.mockMvc.perform(get("/create")).andExpect(status().isOk());
    }
}
