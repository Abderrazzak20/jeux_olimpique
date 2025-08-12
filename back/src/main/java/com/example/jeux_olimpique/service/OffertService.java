package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.DTO.SalesDTO;
import com.example.jeux_olimpique.models.Offert;

public interface OffertService {
	public List<Offert> getAllOffertsAdmin();

	public List<Offert> getActiveOfferts();

	public Optional<Offert> getOffertById(Long id);

	public Offert createOffert(Offert offert);

	public Offert updateOffert(Offert offert);

	public void deleteOffertById(Long id);

	public Offert restoreOffertById(Long id);

	public List<SalesDTO> getSalesPerOffer();

}
