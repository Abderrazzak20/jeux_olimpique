package com.example.jeux_olimpique.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.Enum.ReservationStatus;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.Reservation;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.ReservationRepository;
import com.example.jeux_olimpique.repository.UserRepository;
import com.example.jeux_olimpique.utils.Utilss;
import com.google.zxing.WriterException;

import ch.qos.logback.classic.pattern.Util;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OffertRepository offertRepository;
	@Autowired
	private Utilss utilss;

	public Reservation createReservation(Long userId, Long offertId, int seats) throws WriterException, IOException {
	    
	    String baseUrl = "https://jeuxolimpique-jo2025back.up.railway.app/api/reservation/validate-ticket";

	  
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

	    Offert offert = offertRepository.findById(offertId)
	            .orElseThrow(() -> new RuntimeException("Offre non disponible"));

	    if (offert.getAvailableSeats() < seats) {
	        throw new RuntimeException("Aucune place disponible pour cette offre");
	    }
	    offert.setAvailableSeats(offert.getAvailableSeats() - seats);
	    offertRepository.save(offert);

	    String accountKey = user.getAccountKey();
	    String ticketKey = utilss.generateKey();
	    String finalKey = accountKey + ":" + ticketKey;  
	    String validateUrl = baseUrl + "?finalKey=" + finalKey;
	    String qrCode = utilss.generateQRCode(validateUrl);
	  

	    Reservation reservation = new Reservation();
	    reservation.setExpirationDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
	    reservation.setUser(user);
	    reservation.setOffert(offert);
	    reservation.setSeats(seats);
	    reservation.setTicketKey(ticketKey);
	    reservation.setFinalKey(finalKey);
	    reservation.setQrCode(qrCode);
	    reservation.setStatus(ReservationStatus.VALIDE);

	    return reservationRepository.save(reservation);
	}


	public List<Reservation> getReservationByUser(Long userId) {
		return reservationRepository.findByUserId(userId);
	}

	public List<Reservation> getReservationByOffert(Long offertId) {
		return reservationRepository.findByOffertId(offertId);
	}

	public Reservation updateReservation(Long reservationId, Long newOffertId) throws WriterException, IOException {

		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new RuntimeException("desole on a pas trouve votre reservation"));

		Offert oldOffert = reservation.getOffert();
		Offert newOffert = offertRepository.findById(newOffertId)
				.orElseThrow(() -> new RuntimeException("Nouvelle offre non trouvée"));

		if (newOffert.getAvailableSeats() < reservation.getSeats()) {
			throw new RuntimeException("Aucune place disponible dans la nouvelle offre");

		}
		oldOffert.setAvailableSeats(oldOffert.getAvailableSeats() + reservation.getSeats());
		offertRepository.save(oldOffert);

		newOffert.setAvailableSeats(newOffert.getAvailableSeats() - reservation.getSeats());
		offertRepository.save(newOffert);

		reservation.setOffert(newOffert);

		String ticketKey = utilss.generateKey();

		User user = reservation.getUser();
		String accountKey = user.getAccountKey();
		String finalKey = accountKey + ":" + ticketKey;

		reservation.setFinalKey(finalKey);

		String qrCode = utilss.generateQRCode(finalKey);
		reservation.setQrCode(qrCode);

		return reservationRepository.save(reservation);

	}

	public String deleteReservation(Long reservationId) {

		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

		Offert offert = reservation.getOffert();
		offert.setAvailableSeats(offert.getAvailableSeats() + reservation.getSeats());
		offertRepository.save(offert);

		reservationRepository.deleteById(reservationId);
		return "reservation supprime";
	}

	public Reservation confirmPayment(Long ReservationId) {
		Reservation reservation = reservationRepository.findById(ReservationId)
				.orElseThrow(() -> new RuntimeException("riservation introuvable"));
		reservation.setStatus(ReservationStatus.VALIDE);
		return reservationRepository.save(reservation);

	}
	public void updateExpiredReservations() {
	    List<Reservation> reservations = reservationRepository.findByStatus(ReservationStatus.VALIDE);
	    Date now = new Date();

	    for (Reservation r : reservations) {
	        if (r.getExpirationDate() != null && r.getExpirationDate().before(now)) {
	            r.setStatus(ReservationStatus.EXPIRE);
	            reservationRepository.save(r);
	        }
	    }
	}

	public boolean validateTicket(String finalKey) {
	    Reservation reservation = reservationRepository.findByFinalKey(finalKey);
	    if (reservation == null) {
	        System.out.println("❌ Billet non trouvé");
	        return false;
	    }

	  
	    if (reservation.getStatus() == ReservationStatus.UTILISE) {
	        System.out.println("❌ Billet déjà utilisé");
	        return false;
	    }


	    if (reservation.getExpirationDate() != null && reservation.getExpirationDate().before(new Date())) {
	        System.out.println("❌ Billet expiré");
	        reservation.setStatus(ReservationStatus.EXPIRE); // optionnel, si vous voulez suivre l'état
	        reservationRepository.save(reservation);
	        return false;
	    }

	
	    if (reservation.getStatus() == ReservationStatus.VALIDE) {
	        System.out.println("✅ Billet valide");
	        reservation.setStatus(ReservationStatus.UTILISE);
	        reservationRepository.save(reservation);
	        return true;
	    }

	    return false;
	}


	public List<Reservation> getReservationsByStatus(ReservationStatus status) {
		return reservationRepository.findByStatus(status);
	}
}
