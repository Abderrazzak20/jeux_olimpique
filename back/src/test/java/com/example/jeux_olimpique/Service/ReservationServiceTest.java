package com.example.jeux_olimpique.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.jeux_olimpique.Enum.ReservationStatus;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.ReservationRepository;
import com.example.jeux_olimpique.repository.UserRepository;
import com.example.jeux_olimpique.service.ReservationService;
import com.example.jeux_olimpique.utils.Utilss;
import com.google.zxing.WriterException;

@ExtendWith(SpringExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OffertRepository offertRepository;

    @Mock
    private Utilss utilss;

    @Test
    void createReservation_success() throws WriterException, IOException {
        User user = new User();
        user.setId(1L);
        user.setAccountKey("ACCOUNT_KEY");

        Offert offert = new Offert();
        offert.setId(2L);
        offert.setAvailableSeats(10);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offertRepository.findById(2L)).thenReturn(Optional.of(offert));
        when(utilss.generateKey()).thenReturn("TICKET_KEY");
        when(utilss.generateQRCode(any())).thenReturn("QR_CODE");
        when(reservationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reservation result = reservationService.createReservation(1L, 2L, 5);

        // 🔹 Corretto: finalKey = accountKey + ":" + ticketKey
        assertEquals("ACCOUNT_KEY:TICKET_KEY", result.getFinalKey()); 
        assertEquals("ACCOUNT_KEY", result.getTicketKey());
        assertEquals("QR_CODE", result.getQrCode());

        // 🔹 Verifica anche l'aggiornamento posti
        assertEquals(5, offert.getAvailableSeats());

        verify(offertRepository).save(offert);
        verify(reservationRepository).save(any());
    }

    @Test
    void createReservation_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(1L, 2L, 5);
        });

        assertEquals("user il est pas present", ex.getMessage());
    }

    @Test
    void createReservation_noSeatsAvailable_throwsException() {
        User user = new User();
        Offert offert = new Offert();
        offert.setAvailableSeats(0); // nessun posto

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offertRepository.findById(2L)).thenReturn(Optional.of(offert));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(1L, 2L, 5);
        });

        assertEquals("Aucune place disponible pour cette offre", ex.getMessage());
    }

    @Test
    void getReservationByUser_success() {
        when(reservationRepository.findByUserId(1L)).thenReturn(List.of(new Reservation(), new Reservation()));
        List<Reservation> result = reservationService.getReservationByUser(1L);
        assertEquals(2, result.size());
    }

    @Test
    void updateReservation_success() throws WriterException, IOException {
        User user = new User();
        user.setAccountKey("ACCOUNT_KEY");

        Offert oldOffert = new Offert();
        oldOffert.setAvailableSeats(3);

        Offert newOffert = new Offert();
        newOffert.setId(5L);
        newOffert.setAvailableSeats(4);

        Reservation reservation = new Reservation();
        reservation.setId(10L);
        reservation.setUser(user);
        reservation.setOffert(oldOffert);
        reservation.setSeats(2); // 🔹 aggiunto seats per simulare

        when(reservationRepository.findById(10L)).thenReturn(Optional.of(reservation));
        when(offertRepository.findById(5L)).thenReturn(Optional.of(newOffert));
        when(utilss.generateKey()).thenReturn("NEW_KEY");
        when(utilss.generateQRCode(any())).thenReturn("NEW_QR");
        when(reservationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reservation updated = reservationService.updateReservation(10L, 5L);

        // 🔹 Corretto: finalKey = accountKey + ":" + ticketKey
        assertEquals("ACCOUNT_KEY:NEW_KEY", updated.getFinalKey());
        assertEquals("NEW_QR", updated.getQrCode());
        assertEquals(newOffert, updated.getOffert());

        // 🔹 Verifica aggiornamento posti
        assertEquals(5, oldOffert.getAvailableSeats()); // 3 + 2 liberati
        assertEquals(2, newOffert.getAvailableSeats()); // 4 - 2 occupati
    }

    @Test
    void updateReservation_reservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.updateReservation(1L, 2L);
        });

        assertEquals("desole on a pas trouve votre reservation", ex.getMessage());
    }

    @Test
    void getReservationByOffert_success() {
        when(reservationRepository.findByOffertId(2L))
            .thenReturn(List.of(new Reservation(), new Reservation()));

        List<Reservation> result = reservationService.getReservationByOffert(2L);

        assertEquals(2, result.size());
        verify(reservationRepository).findByOffertId(2L);
    }

    @Test
    void createReservation_offertNotFound() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offertRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(1L, 2L, 5);
        });

        assertEquals("offert n'est pas diposnible", ex.getMessage());
    }

    @Test
    void updateReservation_newOffertNotFound() {
        Reservation reservation = new Reservation();
        reservation.setOffert(new Offert());
        reservation.setUser(new User());
        reservation.setSeats(1);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(offertRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.updateReservation(1L, 2L);
        });

        assertEquals("Nouvelle offre non trouvée", ex.getMessage());
    }

    @Test
    void updateReservation_newOffertFull() {
        Reservation reservation = new Reservation();
        Offert oldOffert = new Offert();
        Offert newOffert = new Offert();
        newOffert.setAvailableSeats(0);

        User user = new User();
        user.setAccountKey("KEY");

        reservation.setUser(user);
        reservation.setOffert(oldOffert);
        reservation.setSeats(1);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(offertRepository.findById(2L)).thenReturn(Optional.of(newOffert));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.updateReservation(1L, 2L);
        });

        assertEquals("Aucune place disponible dans la nouvelle offre", ex.getMessage());
    }

    @Test
    void deleteReservation_notFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.deleteReservation(1L);
        });

        assertEquals("Prenotazione non trovata", ex.getMessage());
    }

    @Test
    void deleteReservation_success() {
        Offert offert = new Offert();
        offert.setAvailableSeats(1);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setOffert(offert);
        reservation.setSeats(1);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        String result = reservationService.deleteReservation(1L);

        // 🔹 Verifica che i posti siano stati liberati
        assertEquals(2, offert.getAvailableSeats());

        verify(offertRepository).save(offert);
        verify(reservationRepository).deleteById(1L);
        assertEquals("reservation supprime", result);
    }

    @Test
    void confirmPayment_success() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reservation result = reservationService.confirmPayment(1L);

        assertEquals(ReservationStatus.PAID, result.getStatus());
    }

    @Test
    void confirmPayment_notFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reservationService.confirmPayment(1L);
        });

        assertEquals("riservation introuvable", ex.getMessage());
    }
}
