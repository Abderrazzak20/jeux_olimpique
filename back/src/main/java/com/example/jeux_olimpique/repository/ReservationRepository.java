package com.example.jeux_olimpique.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jeux_olimpique.Enum.ReservationStatus;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	public List<Reservation> findByOffertId(Long Offertid);
	public List<Reservation> findByUserId(Long userId);
	public Reservation findByFinalKey(String finalKey);
	public List<Reservation> findByStatus(ReservationStatus status);
}
