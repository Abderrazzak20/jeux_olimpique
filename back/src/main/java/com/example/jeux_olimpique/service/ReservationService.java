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

	public Reservation createReservation(Long userId, Long offertId, int seats) throws WriterException, IOException {
		String baseUrl="https://jeux-olimpique.up.railway.app/reservation/validate";
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user il est pas present"));

		Offert offert = offertRepository.findById(offertId)
				.orElseThrow(() -> new RuntimeException("offert n'est pas diposnible"));

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
		reservation.setFinalKey(finalKey);
		reservation.setOffert(offert);
		reservation.setQrCode(qrCode);
		reservation.setTicketKey(ticketKey);
		reservation.setUser(user);
		reservation.setSeats(seats);
		reservation.setStatus(ReservationStatus.PENDING);

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
		reservation.setStatus(ReservationStatus.PAID);
		return reservationRepository.save(reservation);

	}

	public boolean validateTicket(String finalKey) {
		Reservation reservation = reservationRepository.findByFinalKey(finalKey);
		if (reservation == null) {
			return false;
		}

		if (reservation.getStatus() == ReservationStatus.USED) {
			return false;
		}

		if (reservation.getStatus() == ReservationStatus.PAID) {
			reservation.setStatus(ReservationStatus.USED);
			reservationRepository.save(reservation);
			return true;
		}

		return false;
	}

	public String cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new RuntimeException("reservation pas trouvée"));
		Offert offert = reservation.getOffert();
		offert.setAvailableSeats(offert.getAvailableSeats() + reservation.getSeats());
		offertRepository.save(offert);
		reservation.setStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
		return "reservation supprime";
	}

	public List<Reservation> getReservationsByStatus(ReservationStatus status) {
		return reservationRepository.findByStatus(status);
	}
}
