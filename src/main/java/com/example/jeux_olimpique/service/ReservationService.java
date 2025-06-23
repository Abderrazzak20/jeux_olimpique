package com.example.jeux_olimpique.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		Offert offert = offertRepository.findById(offertId).orElseThrow(() -> new RuntimeException("offert pas diposnible"));
		
		String finaleKey = utilss.generateKey();
		String accountKey = user.getAccountKey();
		String data = userId +" "+accountKey+" " + finaleKey;
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

		Offert newOffert = offertRepository.findById(newOffertId)
				.orElseThrow(() -> new RuntimeException("desole on a pas trouve votre reservation"));

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
		reservationRepository.deleteById(reservationId);
		return "reservation supprime";
	}

}
