package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.ReservationRepository;

@Service
public class OffertServiceImpl implements OffertService {

	@Autowired
	private OffertRepository offertRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public List<Offert> getAllOffert() {
		return offertRepository.findAll();
	}

	public Optional<Offert> getOffertById(Long id) {
		if (!offertRepository.existsById(id)) {
			throw new RuntimeException("offert pas trouve");
		}
		return offertRepository.findById(id);
	}

	public Offert createOffert(Offert offert) {
		return offertRepository.save(offert);
	}

	public Offert updateOffert(Offert offert) {
		if (!offertRepository.existsById(offert.getId())) {
			throw new RuntimeException("offerte pas present");
		}
		return offertRepository.save(offert);
	}

	public void deleteOffertById(Long id) {
		if (!offertRepository.existsById(id)) {
			throw new RuntimeException("offert is not find");
		}
		if (!reservationRepository.findByOffertId(id).isEmpty()) {
			throw new RuntimeException("impossible de supprimer : l'offre a déjà des réservations associées.");
		}
		offertRepository.deleteById(id);
	}

}
