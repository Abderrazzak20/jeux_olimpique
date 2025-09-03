import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-validate-ticket',
  imports: [],
  templateUrl: './validate-ticket.html',
  styleUrl: './validate-ticket.css'
})
export class ValidateTicketComponent implements OnInit {
  message: string = '';

  constructor(private route: ActivatedRoute, private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const finalKey = params['finalKey'];
      if (finalKey) {
        this.reservationService.validateTicket(finalKey).subscribe({
          next: res => this.message = res,
          error: err => this.message = err.error
        });
      } else {
        this.message = 'Aucun ticket fourni';
      }
    });
  }
}