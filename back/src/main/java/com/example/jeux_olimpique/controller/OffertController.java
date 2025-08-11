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
	@GetMapping("/active")
	public List<Offert> getallOfferts() {
		return offertService.getActiveOfferts();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public List<Offert> getAllOffertsAdmin() {
		return offertService.getAllOffertsAdmin();
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
	public ResponseEntity<Map<String, Object>> modifieOffert(@RequestBody Offert offert, @PathVariable Long id) {
		offert.setId(id);
		offertService.updateOffert(offert);

		Map<String, Object> res = new HashMap<>();
		res.put("message\", \"modifer avec succes offert id:", id);
		res.put("id", id);
		return ResponseEntity.ok(res);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deletOffert(@PathVariable Long id) {
		offertService.deleteOffertById(id);

		Map<String, Object> res = new HashMap<>();
		res.put("message\",Supprimer avec Succes offert id: ", id);
		res.put("id", id);
		return ResponseEntity.ok(res);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/restore/{id}")
	public ResponseEntity<Map<String, Object>> restoreOffert(@PathVariable Long id) {
	    Offert offert = offertService.restoreOffertById(id);
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Offre restaurée avec succès");
	    response.put("id", offert.getId());
	    return ResponseEntity.ok(response);
	}


}
