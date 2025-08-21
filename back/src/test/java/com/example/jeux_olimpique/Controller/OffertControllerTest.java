package com.example.jeux_olimpique.Controller;

import com.example.jeux_olimpique.controller.OffertController;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.DTO.SalesDTO;
import com.example.jeux_olimpique.security.JwtFilter;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.OffertService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OffertController.class)
@AutoConfigureMockMvc(addFilters = false)
class OffertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffertService offertService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getActiveOfferts_returnsList() throws Exception {
        List<Offert> mockOfferts = List.of(
                new Offert(1L, "Promo1", "Location1", 10, LocalDateTime.now(), 100, 5, "desc1", "img1", false),
                new Offert(2L, "Promo2", "Location2", 20, LocalDateTime.now(), 150, 10, "desc2", "img2", false)
        );

        Mockito.when(offertService.getActiveOfferts()).thenReturn(mockOfferts);

        mockMvc.perform(get("/api/offert/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Promo1"))
                .andExpect(jsonPath("$[1].name").value("Promo2"));
    }

    @Test
    void getOffertById_returnsOffert() throws Exception {
        Offert offert = new Offert(1L, "Promo1", "Location1", 10, LocalDateTime.now(), 100, 5, "desc1", "img1", false);
        Mockito.when(offertService.getOffertById(1L)).thenReturn(Optional.of(offert));

        mockMvc.perform(get("/api/offert/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Promo1"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void createOffert_returnsJsonMessage() throws Exception {
        Offert offert = new Offert(1L, "Promo Created", "Location", 5, LocalDateTime.now(), 50, 3, "desc", "img", false);
        Mockito.when(offertService.createOffert(any())).thenReturn(offert);

        mockMvc.perform(post("/api/offert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Offre créée avec succès"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateOffert_returnsJsonMessage() throws Exception {
        Offert updated = new Offert(1L, "Promo Updated", "Location", 6, LocalDateTime.now(), 60, 4, "desc", "img", false);
        Mockito.when(offertService.updateOffert(any())).thenReturn(updated);

        mockMvc.perform(put("/api/offert/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteOffert_returnsJsonMessage() throws Exception {
        Mockito.doNothing().when(offertService).deleteOffertById(1L);

        mockMvc.perform(delete("/api/offert/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void restoreOffert_returnsJsonMessage() throws Exception {
        Offert offert = new Offert(1L, "Promo Restored", "Location", 5, LocalDateTime.now(), 50, 3, "desc", "img", false);
        Mockito.when(offertService.restoreOffertById(1L)).thenReturn(offert);

        mockMvc.perform(put("/api/offert/restore/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("Offre restaurée avec succès"));
    }

    @Test
    void getSalesPerOffer_returnsSalesList() throws Exception {
        List<SalesDTO> mockSales = List.of(
                new SalesDTO(1L, "Promo1", 100L),
                new SalesDTO(2L, "Promo2", 200L)
        );

        Mockito.when(offertService.getSalesPerOffer()).thenReturn(mockSales);

        mockMvc.perform(get("/api/offert/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].offertName").value("Promo1"))
                .andExpect(jsonPath("$[1].totalSeatsSold").value(200));
    }
}
