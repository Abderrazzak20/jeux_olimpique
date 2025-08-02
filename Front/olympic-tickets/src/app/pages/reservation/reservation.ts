import { OfferteService } from './../../services/offerte-service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { OffertModel } from '../../model/OffertModel';
import { FormsModule } from '@angular/forms';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation',
  imports: [CommonModule, FormsModule],
  templateUrl: './reservation.html',
  styleUrl: './reservation.css'
})
export class Reservation implements OnInit {
  offertM !: OffertModel | null;
  isLoading: boolean = true;
  reservationData = {
    name: "",
    seat: 1
  }

  constructor(private route: ActivatedRoute, private offertS: OfferteService, private reservationS: ReservationService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.offertS.getOffertById(id).subscribe({
      next: (data) => {
        this.offertM = data,
          this.isLoading = false
      },
      error: () => {
        this.offertM = null,
          this.isLoading = false;
      }
    })
  }

  submitReservation() {
    if (!this.offertM) return;

    // validazione frontend contro overbooking
    if (this.reservationData.seat > this.offertM.availableSeats) {
      alert(`Le nombre de places demandées dépasse le nombre de places disponibles (${this.offertM.availableSeats}).`);
      return;
    }

    this.reservationS.createReservation(2, this.offertM.id,this.reservationData.seat).subscribe({
      next: (res) => {
        console.log("Réservation confirmé", res);
        alert("Réservation confirmé !");
         // 🟢 Scala i posti disponibili localmente
      if (this.offertM)
        this.offertM.availableSeats -= this.reservationData.seat;

      // 🧹 Reset del form
      this.reservationData = { name: "", seat: 0 };

      },
      error: (err) => {
        console.error("Erreur de la réservation:", err);
        alert("Erreur lors de la réservation");

      }
    })




  }
}
