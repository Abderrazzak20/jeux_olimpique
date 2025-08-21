package com.example.jeux_olimpique.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.Cart;
import com.example.jeux_olimpique.models.CarteItem;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.CartRepository;
import com.example.jeux_olimpique.repository.CarteItemRepository;
import com.example.jeux_olimpique.repository.OffertRepository;
import com.example.jeux_olimpique.repository.UserRepository;

@Service
public class CarteService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CarteItemRepository carteItemRepository;
	@Autowired
	private OffertRepository offertRepository;

	@Autowired
	private UserRepository userRepository;

	// nuovo carrelo per il cliente ou cercarlo
	public Cart getCartByUser(User user) {
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			cartRepository.save(cart);

		}
		return cart;
	}

	public List<Cart> getAllCart() {
		return cartRepository.findAll();
	}

	public void addOffertToCart(Long userId, Long offerId, int quantity) {
		if (quantity <= 0) {
			throw new RuntimeException("quantité invalide");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("utilisateur non trouvé"));
		Cart cart = getCartByUser(user);

		Offert offert = offertRepository.findById(offerId)
				.orElseThrow(() -> new RuntimeException("offre non disponible"));

		CarteItem items = new CarteItem();
		items.setCart(cart);
		items.setOffert(offert);
		items.setQuantity(quantity);
		carteItemRepository.save(items);

	}

	public List<CarteItem> getCarteItems(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("panier inexistant"));
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			throw new RuntimeException("panier inexistant");
		}
		return cart.getItems();
	}

	public void removeCartItems(Long userId, Long cartItemId) {
		CarteItem item = carteItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("article non trouvé"));

		if (item.getCart().getUser().getId().equals(userId)) {
			carteItemRepository.delete(item);

		} else {
			throw new RuntimeException("impossible de supprimer l'article, utilisateur incorrect");
		}

	}

	public void removeCart(Long cartId) {
		cartRepository.deleteById(cartId);
	}

}
