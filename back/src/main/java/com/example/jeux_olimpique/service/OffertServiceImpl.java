package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.DTO.SalesDTO;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.ReservationRepository;

@Service
public class OffertServiceImpl implements OffertService {

	@Autowired
	private OffertRepository offertRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public List<Offert> getAllOffertsAdmin() {
		return offertRepository.findAll();
	}

	public List<Offert> getActiveOfferts() {
		return offertRepository.findByDeletedFalse();
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
		Offert offert = offertRepository.findById(id).orElseThrow(() -> new RuntimeException("offert is not find"));

		offert.setDeleted(true);
		offertRepository.save(offert);
	}

	public Offert restoreOffertById(Long id) {
		Offert offert = offertRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Offert non trouvé"));
		if (!offert.isDeleted()) {
			throw new RuntimeException("offre non supprime");
		}
		offert.setDeleted(false);
		return offertRepository.save(offert);

	}
	public List<SalesDTO> getSalesPerOffer() {
	    return offertRepository.findSalesPerOffer();
	}


}
