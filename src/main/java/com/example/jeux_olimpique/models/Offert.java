package com.example.jeux_olimpique.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Offert {
	@jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offertId")
    private Long Id;
	
	private String name;
	private Integer max_People;
	private Integer price;
	private int availableSeats;

}
