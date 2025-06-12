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

@Service
public class CarteService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CarteItemRepository carteItemRepository;
	@Autowired
	private OffertRepository offertRepository;

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

	public void addOffertToCart(Long offertId, User user, int quantity) {
		Cart cart = getCartByUser(user);
		Offert offert = offertRepository.findById(offertId)
				.orElseThrow(() -> new RuntimeException("l'offert n'est pas disponible"));

		CarteItem item = new CarteItem();
		item.setCart(cart);
		item.setOffert(offert);
		item.setQuantity(quantity);
		carteItemRepository.save(item);
	}

	public List<CarteItem> getCarteItems(User user) {
		Cart cart = getCartByUser(user);
		return cart.getItems();
	}

	public void removeCartItems(User user, Long carteId) {
		CarteItem item = carteItemRepository.findById(carteId)
				.orElseThrow(() -> new RuntimeException("item pas trouvé"));
		if (item.getCart().getUser().getId().equals(user.getId())) {
			carteItemRepository.delete(item);
		} else {
			throw new RuntimeException("element impossible a supperimer pk il est pas trouve");
		}

	}

}
