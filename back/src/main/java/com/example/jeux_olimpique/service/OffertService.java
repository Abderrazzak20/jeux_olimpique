package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.Offert;


public interface OffertService {
	public List<Offert> getAllOffert();

	public Optional<Offert> getOffertById(Long id);

	public Offert createOffert(Offert offert);

	public Offert updateOffert(Offert offert);

	public void deleteOffertById(Long id);
}
