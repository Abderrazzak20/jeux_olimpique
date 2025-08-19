/*package com.example.jeux_olimpique.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.ReservationRepository;
import com.example.jeux_olimpique.service.OffertServiceImpl;

@ExtendWith(SpringExtension.class)
public class OffertServiceImplTest {

    @InjectMocks
    private OffertServiceImpl offertService;

    @Mock
    private OffertRepository offertRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    void getAllOffert_returnsList() {
        List<Offert> offertList = List.of(new Offert(), new Offert());
        when(offertRepository.findAll()).thenReturn(offertList);

        List<Offert> result = offertService.getAllOffert();

        assertEquals(2, result.size());
        verify(offertRepository).findAll();
    }

    @Test
    void getOffertById_exists_returnsOffert() {
        Offert offert = new Offert();
        offert.setId(1L);

        when(offertRepository.existsById(1L)).thenReturn(true);
        when(offertRepository.findById(1L)).thenReturn(Optional.of(offert));

        Optional<Offert> result = offertService.getOffertById(1L);

        assertTrue(result.isPresent());
        assertEquals(offert, result.get());
        verify(offertRepository).existsById(1L);
        verify(offertRepository).findById(1L);
    }

    @Test
    void getOffertById_notExists_throwsException() {
        when(offertRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            offertService.getOffertById(1L);
        });

        assertEquals("offert pas trouve", ex.getMessage());
        verify(offertRepository).existsById(1L);
        verify(offertRepository, never()).findById(any());
    }

    @Test
    void createOffert_savesAndReturns() {
        Offert offert = new Offert();
        when(offertRepository.save(offert)).thenReturn(offert);

        Offert result = offertService.createOffert(offert);

        assertEquals(offert, result);
        verify(offertRepository).save(offert);
    }

    @Test
    void updateOffert_exists_savesAndReturns() {
        Offert offert = new Offert();
        offert.setId(1L);

        when(offertRepository.existsById(1L)).thenReturn(true);
        when(offertRepository.save(offert)).thenReturn(offert);

        Offert result = offertService.updateOffert(offert);

        assertEquals(offert, result);
        verify(offertRepository).existsById(1L);
        verify(offertRepository).save(offert);
    }

    @Test
    void updateOffert_notExists_throwsException() {
        Offert offert = new Offert();
        offert.setId(1L);

        when(offertRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            offertService.updateOffert(offert);
        });

        assertEquals("offerte pas present", ex.getMessage());
        verify(offertRepository).existsById(1L);
        verify(offertRepository, never()).save(any());
    }

    @Test
    void deleteOffertById_notExists_throwsException() {
        when(offertRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            offertService.deleteOffertById(1L);
        });

        assertEquals("offert is not find", ex.getMessage());
        verify(offertRepository).existsById(1L);
        verify(reservationRepository, never()).findByOffertId(anyLong());
        verify(offertRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteOffertById_hasReservations_throwsException() {
        when(offertRepository.existsById(1L)).thenReturn(true);
        // Creo una lista con un oggetto Reservation fittizio
        when(reservationRepository.findByOffertId(1L)).thenReturn(List.of(new Reservation()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            offertService.deleteOffertById(1L);
        });

        assertEquals("impossible de supprimer : l'offre a déjà des réservations associées.", ex.getMessage());
        verify(offertRepository).existsById(1L);
        verify(reservationRepository).findByOffertId(1L);
        verify(offertRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteOffertById_noReservations_deletesSuccessfully() {
        when(offertRepository.existsById(1L)).thenReturn(true);
        when(reservationRepository.findByOffertId(1L)).thenReturn(new ArrayList<>());

        offertService.deleteOffertById(1L);

        verify(offertRepository).existsById(1L);
        verify(reservationRepository).findByOffertId(1L);
        verify(offertRepository).deleteById(1L);
    }
}
*/