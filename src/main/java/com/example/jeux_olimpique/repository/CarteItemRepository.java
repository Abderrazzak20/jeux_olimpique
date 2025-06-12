package com.example.jeux_olimpique.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jeux_olimpique.models.CarteItem;

public interface CarteItemRepository extends JpaRepository<CarteItem, Long> {

}
