package com.example.jeux_olimpique.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.service.OffertService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/offert")
public class OffertController {

	@Autowired
	private OffertService offertService;

	@PermitAll
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
	public ResponseEntity<Map<String, Object>> createOffert(@RequestBody Offert offert) {
	    offertService.createOffert(offert);
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Offre créée avec succès");
	    response.put("id", offert.getId());
	    return ResponseEntity.ok(response);
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
