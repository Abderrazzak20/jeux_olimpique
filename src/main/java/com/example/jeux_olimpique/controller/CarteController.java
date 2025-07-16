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

	@GetMapping // tutti i carrelli presenti
	public List<Cart> getCart() {
		return carteService.getAllCart();
	}

	@GetMapping("/{userId}/items") // tutti i prodotti presenti per un user
	public List<CarteItem> getAllItems(@PathVariable Long userId) {
		return carteService.getCarteItems(userId);
	}

	@PostMapping("/{userId}/") // aggiungi un offerta al carrello
	public void addOffertToCart(@PathVariable Long userId, @RequestParam Long offerid, @RequestParam int quantity) {
		carteService.addOffertToCart(userId, offerid, quantity);
	}

	@DeleteMapping("items/{userId}/") // elima un prodotto nel carrello
	public String deletecarteItem(@PathVariable Long userId, @RequestParam Long cartItemId) {
		carteService.removeCartItems(userId, cartItemId);
		return "produit elimine";
	}

	@DeleteMapping("{cartId}")
	public String deleteCarte(@PathVariable Long cartId) {
		carteService.removeCart(cartId);
		return "cart supprime avec succes";

	}
}


