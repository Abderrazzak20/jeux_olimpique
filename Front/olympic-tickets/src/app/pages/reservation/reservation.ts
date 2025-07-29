import { OfferteService } from './../../services/offerte-service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { OffertModel } from '../../model/OffertModel';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reservation',
  imports: [CommonModule,FormsModule],
  templateUrl: './reservation.html',
  styleUrl: './reservation.css'
})
export class Reservation implements OnInit {
  offertM !: OffertModel | null;
  isLoading: boolean = true;
  reservationData = {
    name: "",
    seats: 1
  }

  constructor(private route: ActivatedRoute, private offertS: OfferteService) { }

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

  submitReservation(){
    console.log("reservation envoye",this.reservationData);
    alert("reservation confirme");
    
  }
}
