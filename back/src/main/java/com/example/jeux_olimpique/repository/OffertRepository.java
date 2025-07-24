package com.example.jeux_olimpique.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jeux_olimpique.models.Cart;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.User;

public interface OffertRepository extends JpaRepository<Offert, Long> {
	//Cart cartFindByUser(User user);

}
