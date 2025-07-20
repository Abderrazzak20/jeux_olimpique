package com.example.jeux_olimpique.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public String createOffert(@RequestBody Offert offert) {
		 offertService.createOffert(offert);
		 return "Add offert with succes offert id:"+offert.getId();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public String modifieOffert(@RequestBody Offert offert,@PathVariable Long id) {
		 offertService.updateOffert(offert);
		 return "modifer avec succes offert id: "+id;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public String deletOffert(@PathVariable Long id) {
		offertService.deleteOffertById(id);
		return "Supprimer avec Succes offert id: "+id;
	}

}
