package com.example.jeux_olimpique.models;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.example.jeux_olimpique.Enum.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Column(name = "reservationId")
	private Long Id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Offert offert;
    @Column(nullable = false)
    private int seats;
    
    private String ticketKey;
    private String finalKey;
    @Column(length = 2048)
    private String qrCode;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime expirationDate;

}
