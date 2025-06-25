package com.example.jeux_olimpique.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

	public Reservation createReservation(Long userId, Long offertId) throws WriterException, IOException {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user il est pas present"));

		Offert offert = offertRepository.findById(offertId)
				.orElseThrow(() -> new RuntimeException("offert n'est pas diposnible"));

		if (offert.getAvailableSeats() <= 0) {
			throw new RuntimeException("Aucune place disponible pour cette offre");

		}
		offert.setAvailableSeats(offert.getAvailableSeats() - 1);
		offertRepository.save(offert);

		String finaleKey = utilss.generateKey();
		String accountKey = user.getAccountKey();
		String data = userId + " " + accountKey + " " + finaleKey;
		String qrCode = utilss.generateQRCode(data);

		Reservation reservation = new Reservation();
		reservation.setFinalKey(finaleKey);
		reservation.setOffert(offert);
		reservation.setQrCode(qrCode);
		reservation.setTicketKey(accountKey);
		reservation.setUser(user);

		return reservationRepository.save(reservation);
	}

	// tutte le prenotazioni di un utente
	public List<Reservation> getReservationByUser(Long userId) {
		return reservationRepository.findByUserId(userId);
	}

	// Lista prenotazioni per un'offerta
	public List<Reservation> getReservationByOffert(Long offertId) {
		return reservationRepository.findByOffertId(offertId);
	}

	// Aggiornamento prenotazione
	public Reservation updateReservation(Long reservationId, Long newOffertId) throws WriterException, IOException {

		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new RuntimeException("desole on a pas trouve votre reservation"));

		Offert oldOffert = reservation.getOffert();
		Offert newOffert = offertRepository.findById(newOffertId)
				.orElseThrow(() -> new RuntimeException("Nouvelle offre non trouvée"));
		
		if (newOffert.getAvailableSeats()<=0) {
			throw new RuntimeException("Aucune place disponible dans la nouvelle offre");
			
		}
		oldOffert.setAvailableSeats(oldOffert.getAvailableSeats()+1);
		offertRepository.save(oldOffert);
		
		newOffert.setAvailableSeats(newOffert.getAvailableSeats()-1);
		offertRepository.save(newOffert);
		
		reservation.setOffert(newOffert);
		String finalKey = utilss.generateKey();
		reservation.setFinalKey(finalKey);

		User user = reservation.getUser();
		String firstKey = user.getAccountKey();

		String data = reservationId + " " + firstKey + " " + finalKey;
		String qrCode = utilss.generateQRCode(data);
		reservation.setQrCode(qrCode);

		return reservationRepository.save(reservation);

	}

	public String deleteReservation(Long reservationId) {

		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

		Offert offert = reservation.getOffert();
		offert.setAvailableSeats(offert.getAvailableSeats() + 1);
		offertRepository.save(offert);

		reservationRepository.deleteById(reservationId);
		return "reservation supprime";
	}

	// confirmation paimenent
	public Reservation confirmPayment(Long ReservationId) {
		Reservation reservation = reservationRepository.findById(ReservationId)
				.orElseThrow(() -> new RuntimeException("riservation introuvable"));
		reservation.setStatus(ReservationStatus.PAID);
		return reservationRepository.save(reservation);

	}

}
