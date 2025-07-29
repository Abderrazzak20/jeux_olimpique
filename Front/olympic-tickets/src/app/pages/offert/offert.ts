import { OfferteService } from './../../services/offerte-service';
import { OffertModel } from './../../model/OffertModel';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-offert',
  imports: [CommonModule,RouterLink],
  templateUrl: './offert.html',
  styleUrl: './offert.css'
})
export class Offerts implements OnInit {
  offertlist: OffertModel[] = [];
  isLoading: boolean = true;

  constructor(private OfferteService: OfferteService) { }

ngOnInit(): void {
    this.OfferteService.getOffert().subscribe({
      next: (data) => {
        this.offertlist = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des offres :", err);
        this.isLoading = false;
      }
    });
  }

}
