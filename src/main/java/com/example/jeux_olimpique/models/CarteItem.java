package com.example.jeux_olimpique.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarteItem {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartItemId")
	private Long Id;
	@ManyToOne
	private Offert offert;
	
	@ManyToOne
	@JsonBackReference
	private Cart cart;
	private int quantity;
}
