package com.example.jeux_olimpique.Controller;

import com.example.jeux_olimpique.controller.ReservationController;
import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.security.JwtFilter;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.ReservationService;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private JwtService jwtService; // MOCK della dipendenza mancante

    @MockBean
    private JwtFilter jwtFilter; // MOCK del filtro se incluso nei config
    @Test
    void testCreateReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        Mockito.when(reservationService.createReservation(1L, 2L,5)).thenReturn(reservation);

        mockMvc.perform(post("/api/reservation")
                .param("userId", "1")
                .param("offertId", "2")
                .param("seat", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateReservationThrowsException() throws Exception {
        Mockito.when(reservationService.createReservation(anyLong(), anyLong(),anyInt()))
                .thenThrow(new RuntimeException("Errore durante la prenotazione"));

        mockMvc.perform(post("/api/reservation")
                .param("userId", "1")
                .param("offertId", "2")
                .param("seat", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante la prenotazione"));
    }

    @Test
    void testGetReservationByUser() throws Exception {
        Reservation r1 = new Reservation();
        r1.setId(1L);
        Reservation r2 = new Reservation();
        r2.setId(2L);
        List<Reservation> reservations = Arrays.asList(r1, r2);

        Mockito.when(reservationService.getReservationByUser(1L)).thenReturn(reservations);

        mockMvc.perform(get("/api/reservation/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetReservationByOffert() throws Exception {
        Reservation r = new Reservation();
        r.setId(10L);

        Mockito.when(reservationService.getReservationByOffert(99L))
                .thenReturn(List.of(r));

        mockMvc.perform(get("/api/reservation/offert/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L));
    }

    @Test
    void testUpdateReservation() throws Exception {
        Reservation updated = new Reservation();
        updated.setId(5L);

        Mockito.when(reservationService.updateReservation(5L, 20L)).thenReturn(updated);

        mockMvc.perform(put("/api/reservation/5")
                .param("newOffertId", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));
    }

    @Test
    void testDeleteReservation() throws Exception {
        Mockito.when(reservationService.deleteReservation(3L))
                .thenReturn("Reservation deleted");

        mockMvc.perform(delete("/api/reservation/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation deleted"));
    }
}
