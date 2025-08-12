package com.example.jeux_olimpique.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jeux_olimpique.DTO.SalesDTO;
import com.example.jeux_olimpique.models.Cart;
import com.example.jeux_olimpique.models.Offert;
import com.example.jeux_olimpique.models.User;

public interface OffertRepository extends JpaRepository<Offert, Long> {
	List<Offert> findByDeletedFalse();

	@Query("SELECT new com.example.jeux_olimpique.DTO.SalesDTO(offert.id, offert.name, COALESCE(SUM(reservation.seats), 0)) "
			+ "FROM Offert offert LEFT JOIN Reservation reservation ON reservation.offert = offert "
			+ "GROUP BY offert.id, offert.name")
	List<SalesDTO> findSalesPerOffer();

}
