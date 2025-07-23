package com.example.jeux_olimpique.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.example.jeux_olimpique.models.*;
import com.example.jeux_olimpique.repository.*;
import com.example.jeux_olimpique.service.CarteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CarteServiceTest {

	@InjectMocks
	private CarteService carteService;

	@Mock
	private CartRepository cartRepository;
	@Mock
	private CarteItemRepository carteItemRepository;
	@Mock
	private OffertRepository offertRepository;
	@Mock
	private UserRepository userRepository;

	@Test
	void getCartByUser_existingCart_returnsCart() {
		User user = new User();
		Cart cart = new Cart();
		when(cartRepository.findByUser(user)).thenReturn(cart);

		Cart result = carteService.getCartByUser(user);

		assertEquals(cart, result);
		verify(cartRepository, never()).save(any());
	}

	@Test
	void getCartByUser_newCart_createdAndSaved() {
		User user = new User();
		when(cartRepository.findByUser(user)).thenReturn(null);
		when(cartRepository.save(any())).thenAnswer(i -> i.getArgument(0));

		Cart result = carteService.getCartByUser(user);

		assertNotNull(result);
		assertEquals(user, result.getUser());
		verify(cartRepository).save(any());
	}

	@Test
    void getAllCart_returnsAllCarts() {
        when(cartRepository.findAll()).thenReturn(List.of(new Cart(), new Cart()));

        List<Cart> result = carteService.getAllCart();

        assertEquals(2, result.size());
    }

	@Test
	void addOffertToCart_success() {
		User user = new User();
		user.setId(1L);
		Cart cart = new Cart();
		Offert offert = new Offert();

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(cart);
		when(offertRepository.findById(2L)).thenReturn(Optional.of(offert));

		carteService.addOffertToCart(1L, 2L, 3);

		verify(carteItemRepository).save(any(CarteItem.class));
	}

	@Test
    void addOffertToCart_userNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            carteService.addOffertToCart(1L, 2L, 1);
        });

        assertEquals("user il est pas trouve", ex.getMessage());
    }

	@Test
	void addOffertToCart_offerNotFound_throwsException() {
		User user = new User();
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(new Cart());
		when(offertRepository.findById(2L)).thenReturn(Optional.empty());

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			carteService.addOffertToCart(1L, 2L, 1);
		});

		assertEquals("l'offert n'est pas disponible", ex.getMessage());
	}

	@Test
	void getCarteItems_success() {
		User user = new User();
		Cart cart = new Cart();
		cart.setItems(List.of(new CarteItem(), new CarteItem()));

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(cart);

		List<CarteItem> items = carteService.getCarteItems(1L);

		assertEquals(2, items.size());
	}


	@Test
	void getCarteItems_cartIsNull_throwsException() {
		User user = new User();
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(null);

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			carteService.getCarteItems(1L);
		});

		assertEquals("carrello inesistente", ex.getMessage());
	}

	@Test
	void removeCartItems_success() {
		User user = new User();
		user.setId(1L);

		Cart cart = new Cart();
		cart.setUser(user);

		CarteItem item = new CarteItem();
		item.setCart(cart);

		when(carteItemRepository.findById(1L)).thenReturn(Optional.of(item));

		carteService.removeCartItems(1L, 1L);

		verify(carteItemRepository).delete(item);
	}

	@Test
	void removeCartItems_wrongUser() {
		User correctUser = new User();
		correctUser.setId(1L);

		User wrongUser = new User();
		wrongUser.setId(2L);

		Cart cart = new Cart();
		cart.setUser(wrongUser);

		CarteItem item = new CarteItem();
		item.setCart(cart);

		when(carteItemRepository.findById(1L)).thenReturn(Optional.of(item));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			carteService.removeCartItems(1L, 1L); // userId ≠ item.getCart().getUser().getId()
		});

		assertEquals("element impossible a supperimer pk il est pas trouve", ex.getMessage());
		verify(carteItemRepository, never()).delete(any());
	}

	@Test
    void removeCartItems_itemNotFound() {
        when(carteItemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            carteService.removeCartItems(1L, 1L);
        });

        assertEquals("item pas trouvé", ex.getMessage());
    }

	@Test
	void removeCart_success() {
		Long cartId = 1L;

		// Nessuna eccezione attesa: il metodo chiama direttamente deleteById
		carteService.removeCart(cartId);

		// Verifica che deleteById sia stato invocato con l'ID corretto
		verify(cartRepository).deleteById(cartId);
	}

	@Test
	void removeCart_existingCart_deletesSuccessfully() {
		carteService.removeCart(1L);
		verify(cartRepository).deleteById(1L);
	}

	@Test
	void getCarteItems_cartNull_throwsException() {
		User user = new User();
		user.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(null);

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			carteService.getCarteItems(1L);
		});

		assertEquals("carrello inesistente", ex.getMessage());
	}

	@Test
	void removeCartItems_wrongUser_throwsException() {
		CarteItem item = new CarteItem();
		Cart cart = new Cart();
		User user = new User();
		user.setId(2L); // User diverso

		cart.setUser(user);
		item.setCart(cart);

		when(carteItemRepository.findById(1L)).thenReturn(Optional.of(item));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			carteService.removeCartItems(1L, 1L); // userId diverso
		});

		assertEquals("element impossible a supperimer pk il est pas trouve", ex.getMessage());
	}
	@Test
	void removeCart_cartNotFound_throwsException() {
	    doThrow(new RuntimeException("cart non trovato")).when(cartRepository).deleteById(99L);

	    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
	        carteService.removeCart(99L);
	    });

	    assertEquals("cart non trovato", ex.getMessage());
	}


}