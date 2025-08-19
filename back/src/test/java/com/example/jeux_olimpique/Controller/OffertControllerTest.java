/*package com.example.jeux_olimpique.Controller;

import com.example.jeux_olimpique.controller.OffertController;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.security.JwtFilter;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.OffertService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

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
    private JwtService jwtService; // MOCK della dipendenza mancante

    @MockBean
    private JwtFilter jwtFilter; // MOCK del filtro se incluso nei config

    @Test
    void getAllOfferts_returnsList() throws Exception {
        List<Offert> mockOfferts = List.of(
                new Offert(1L, "Promo1", 10, 100, 5,"description"),
                new Offert(2L, "Promo2", 20, 150, 10,"description")
        );

        Mockito.when(offertService.getAllOffert()).thenReturn(mockOfferts);

        mockMvc.perform(get("/api/offert"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Promo1"))
                .andExpect(jsonPath("$[1].name").value("Promo2"));
    }

    @Test
    void getOffertById_returnsOffert() throws Exception {
        Offert offert = new Offert(1L, "Promo1", 10, 100, 5,"description");
        Mockito.when(offertService.getOffertById(1L)).thenReturn(Optional.of(offert));

        mockMvc.perform(get("/api/offert/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Promo1"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void createOffert_returnsSuccessMessage() throws Exception {
        Offert offert = new Offert(1L, "Promo Created", 5, 50, 3,"description");
        Mockito.when(offertService.createOffert(any())).thenReturn(offert);

        mockMvc.perform(post("/api/offert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offert)))
                .andExpect(status().isOk())
                .andExpect(content().string("Add offert with succes offert id:1"));
    }

    @Test
    void updateOffert_returnsSuccessMessage() throws Exception {
        Offert updated = new Offert(1L, "Promo Updated", 6, 60, 4,"description");
        Mockito.when(offertService.updateOffert(any())).thenReturn(updated);

        mockMvc.perform(put("/api/offert/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().string("modifer avec succes offert id: 1"));
    }

    @Test
    void deleteOffert_returnsSuccessMessage() throws Exception {
        Mockito.doNothing().when(offertService).deleteOffertById(1L);

        mockMvc.perform(delete("/api/offert/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Supprimer avec Succes offert id: 1"));
    }
}*/
