package com.example.jeux_olimpique.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.service.OffertService;

@RestController
@RequestMapping("/api/offert")
public class OffertController {

	@Autowired
	private OffertService offertService;

	@GetMapping
	public List<Offert> getallOfferts() {
		return offertService.getAllOffert();
	}
	
	@GetMapping("/{id}")
	public Optional<Offert> getOffertById(@PathVariable Long id) {
		return offertService.getOffertById(id);
	}

	@PostMapping
	public Offert createOffert(@RequestBody Offert offert) {
		return offertService.createOffert(offert);
	}
	
	@PutMapping("/{id}")
	public Offert modifieOffert(@RequestBody Offert offert,@PathVariable Long id) {
		return offertService.updateOffert(offert);
	}
	
	@DeleteMapping("/{id}")
	public void deletOffert(@PathVariable Long id) {
		offertService.deleteOffertById(id);
	}

}
