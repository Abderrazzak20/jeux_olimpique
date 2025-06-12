package com.example.jeux_olimpique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jeux_olimpique.models.Cart;
import com.example.jeux_olimpique.models.CarteItem;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.service.CarteService;

@RestController
@RequestMapping("/api/cart")
public class CarteController {

	@Autowired
	private CarteService carteService;

	@GetMapping
	public List<CarteItem> gatCartItems(User user) {
		return carteService.getCarteItems(user);
	}

	@PostMapping("/{id}/")
	public void addOffertToCart(User user, @RequestParam Long offerid, @RequestParam int quantity) {
		carteService.addOffertToCart(offerid, user, quantity);
	}
	
	@DeleteMapping("/{id}/")
	public void deletecarteItem(User user,@PathVariable Long id) {
		carteService.removeCartItems(user, id);
	}

}
