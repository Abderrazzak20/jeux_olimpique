package com.example.jeux_olimpique.Controller;

import com.example.jeux_olimpique.controller.CarteController;
import com.example.jeux_olimpique.models.Cart;
import com.example.jeux_olimpique.models.CarteItem;
import com.example.jeux_olimpique.security.CustomUserDetailsService;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.CarteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarteService carteService;
    
    @MockBean
	private JwtService jwtService;
	@MockBean
	private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCart_returnsListOfCarts() throws Exception {
        Cart cart1 = new Cart();
        cart1.setId(1L);
        Cart cart2 = new Cart();
        cart2.setId(2L);

        when(carteService.getAllCart()).thenReturn(Arrays.asList(cart1, cart2));

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(cart1, cart2))));
    }

    @Test
    void testGetAllItems_returnsListOfCarteItems() throws Exception {
        CarteItem item1 = new CarteItem();
        item1.setId(1L);
        CarteItem item2 = new CarteItem();
        item2.setId(2L);

        when(carteService.getCarteItems(10L)).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/cart/10/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(item1, item2))));
    }

    @Test
    void testAddOffertToCart_returnsSuccessMessage() throws Exception {
        doNothing().when(carteService).addOffertToCart(10L, 5L, 3);

        mockMvc.perform(post("/api/cart/10")
                .param("offerid", "5")
                .param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("offert added to Cart"));
    }

    @Test
    void testDeleteCarteItem_returnsSuccessMessage() throws Exception {
        doNothing().when(carteService).removeCartItems(10L, 3L);

        mockMvc.perform(delete("/api/cart/10/items")
                .param("cartItemId", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("produit elimine"));
    }

    @Test
    void testDeleteCarte_returnsSuccessMessage() throws Exception {
        doNothing().when(carteService).removeCart(7L);

        mockMvc.perform(delete("/api/cart/7"))
                .andExpect(status().isOk())
                .andExpect(content().string("cart supprime avec succes"));
    }
}
