
package com.example.borrow;

import com.example.borrow.dto.BorrowRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowRequestDto sample;

    @BeforeEach
    void init() {
        sample = new BorrowRequestDto(null, "Alice", 101L, LocalDate.now(), LocalDate.now().plusDays(7), "PENDING");
    }

    @Test
    void createBorrowRequest_returns201AndBody() throws Exception {
        mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/borrow-requests/")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.borrowerName", is("Alice")))
                .andExpect(jsonPath("$.itemId", is(101)));
    }

    @Test
    void getBorrowRequest_returns200() throws Exception {
        String response = mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        BorrowRequestDto created = objectMapper.readValue(response, BorrowRequestDto.class);

        mockMvc.perform(get("/borrow-requests/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(created.getId().intValue())))
                .andExpect(jsonPath("$.borrowerName", is("Alice")));
    }

    @Test
    void getBorrowRequest_notFound_returns404() throws Exception {
        mockMvc.perform(get("/borrow-requests/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBorrowRequest_returns200() throws Exception {
        String response = mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        BorrowRequestDto created = objectMapper.readValue(response, BorrowRequestDto.class);

        BorrowRequestDto update = new BorrowRequestDto(created.getId(), "Bob", 202L, created.getStartDate(), created.getEndDate(), "APPROVED");
        mockMvc.perform(put("/borrow-requests/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowerName", is("Bob")))
                .andExpect(jsonPath("$.status", is("APPROVED")));
    }

    @Test
    void updateBorrowRequest_notFound_returns404() throws Exception {
        BorrowRequestDto update = new BorrowRequestDto(99999L, "Bob", 202L, LocalDate.now(), LocalDate.now().plusDays(3), "PENDING");
        mockMvc.perform(put("/borrow-requests/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBorrowRequest_returns204() throws Exception {
        String response = mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        BorrowRequestDto created = objectMapper.readValue(response, BorrowRequestDto.class);

        mockMvc.perform(delete("/borrow-requests/" + created.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBorrowRequest_notFound_returns404() throws Exception {
        mockMvc.perform(delete("/borrow-requests/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_returnsList() throws Exception {
        // create two
        mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated());
        BorrowRequestDto second = new BorrowRequestDto(null, "Carol", 303L, LocalDate.now(), LocalDate.now().plusDays(5), "PENDING");
        mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(second)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/borrow-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void validation_onCreate_missingBorrowerName_returns400() throws Exception {
        BorrowRequestDto invalid = new BorrowRequestDto(null, "", 404L, LocalDate.now(), LocalDate.now().plusDays(1), "PENDING");
        mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dtoConversion_methods_workAsExpected() throws Exception {
        // Simple smoke test via controller create/get to ensure conversions preserve data
        String response = mockMvc.perform(post("/borrow-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        BorrowRequestDto created = objectMapper.readValue(response, BorrowRequestDto.class);

        mockMvc.perform(get("/borrow-requests/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowerName", is(sample.getBorrowerName())))
                .andExpect(jsonPath("$.itemId", is(sample.getItemId().intValue())))
                .andExpect(jsonPath("$.status", is(sample.getStatus())));
    }
}
