package com.example.jeux_olimpique.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.service.ReservationService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestParam Long userId, @RequestParam Long offertId) {
	    try {
	        Reservation reservation = reservationService.createReservation(userId, offertId);
	        return ResponseEntity.ok(reservation);
	    } catch (RuntimeException | WriterException | IOException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}



	// Ottieni tutte le prenotazioni di un utente
	@GetMapping("/user/{userId}")
	public List<Reservation> getReservationByUser(@PathVariable Long userId) {

		List<Reservation> listReservationByUser = reservationService.getReservationByUser(userId);
		return listReservationByUser;
	}

	// Ottieni tutte le prenotazioni di un offert
	@GetMapping("/offert/{offertId}")
	public List<Reservation> getReservationByOffert(@PathVariable Long offertId) {

		List<Reservation> listReservationByOffert = reservationService.getReservationByOffert(offertId);
		return listReservationByOffert;
	}
	

    // Aggiorna una prenotazione (cambia offerta)
    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservation( @PathVariable Long reservationId,@RequestParam Long newOffertId) throws WriterException, IOException {
        Reservation reservation = reservationService.updateReservation(reservationId, newOffertId);
        return ResponseEntity.ok(reservation);
    }

    // Elimina una prenotazione
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        String message = reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(message);
    }
	
	
}
