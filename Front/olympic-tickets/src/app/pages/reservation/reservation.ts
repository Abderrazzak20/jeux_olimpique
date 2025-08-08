import { Offerts } from './../offert/offert';
import { AutherService } from './../../services/auther-service';
import { ReservationService } from './../../services/reservation-service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-reservation',
  imports: [CommonModule, FormsModule],
  templateUrl: './reservation.html',
  styleUrl: './reservation.css'
})
export class Reservation implements OnInit {
  reservations: any[] = [];
  isLoading: boolean = false;

  constructor(private ReservatioS: ReservationService, private AutherS: AutherService, private router: Router) { }
  ngOnInit(): void {
    const userId = this.AutherS.getUserId();
    if (!userId) {
      alert("vouz devez être connecté.");
      this.router.navigate(["/login"]);
      return;
    }
    this.isLoading = true;
    this.ReservatioS.getUserReservationId(userId).subscribe({
      next: (data) => {
        console.log("tutto qui",data);
        
        this.reservations = data;
        this.isLoading = false;
      },
      error: () => {
        alert("Erreur lors du chargement des réservations.");
        this.isLoading = false;
      }
    })
  }
}
