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

import com.example.jeux_olimpique.Enum.ReservationStatus;
import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.service.ReservationService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestParam Long userId, @RequestParam Long offertId,
			@RequestParam int seat) {
		try {
			Reservation reservation = reservationService.createReservation(userId, offertId, seat);
			return ResponseEntity.ok(reservation);
		} catch (RuntimeException | WriterException | IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/user/{userId}")
	public List<Reservation> getReservationByUser(@PathVariable Long userId) {

		List<Reservation> listReservationByUser = reservationService.getReservationByUser(userId);
		listReservationByUser.forEach(r -> {
			System.out.println(r.getOffert()); 
		});
		return listReservationByUser;
	}

	@GetMapping("/offert/{offertId}")
	public List<Reservation> getReservationByOffert(@PathVariable Long offertId) {

		List<Reservation> listReservationByOffert = reservationService.getReservationByOffert(offertId);
		return listReservationByOffert;
	}

	@PutMapping("/{reservationId}")
	public ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationId,
			@RequestParam Long newOffertId) throws WriterException, IOException {
		Reservation reservation = reservationService.updateReservation(reservationId, newOffertId);
		return ResponseEntity.ok(reservation);
	}
	@PostMapping("/{reservationId}/pay")
	public ResponseEntity<Reservation> confirmPayment(@PathVariable Long reservationId) {
		Reservation reservation = reservationService.confirmPayment(reservationId);
		return ResponseEntity.ok(reservation);
	}

	@GetMapping("/validate-ticket")
	public ResponseEntity<String> validateTicketPublic(@RequestParam String finalKey) {
	    try {
	        boolean valid = reservationService.validateTicket(finalKey);
	        if (valid) {
	            return ResponseEntity.ok("✅ Billet valide et utilisé, la clé finale est = "+finalKey);
	        } else {
	            return ResponseEntity.badRequest().body("❌ Billet non valide ou déjà utilisé, la clé finale est = "+finalKey);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Errore interno: " + e.getMessage());
	    }
	}


	@GetMapping("/status")
	public List<Reservation> getReservationByStatus(@RequestParam ReservationStatus status) {
	    return reservationService.getReservationsByStatus(status);
	}

}
