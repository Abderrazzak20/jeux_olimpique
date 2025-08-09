package com.example.jeux_olimpique.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offertId")
	private Long Id;

	private String name;
	private String location;
	private Integer max_People;
	
	private LocalDateTime date;
	private Integer price;
	private int availableSeats;
	private String description;
	private String imageUrl;

}
