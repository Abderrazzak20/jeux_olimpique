package com.example.jeux_olimpique.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Reservation {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Offert offert;
    
    private String ticketKey;
    private String finalKey;
    private String qrCode;
}
