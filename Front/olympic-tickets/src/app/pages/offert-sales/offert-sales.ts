import { Component, OnInit } from '@angular/core';
import { SalesDTO } from '../../model/SalesDTO';
import { OfferteService } from '../../services/offerte-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-offert-sales',
  imports: [CommonModule],
  templateUrl: './offert-sales.html',
  styleUrl: './offert-sales.css'
})
export class OffertSales implements OnInit {

  listeVentes: SalesDTO[] = [];
  errroMessage: string | null = null;

  constructor(private offertS: OfferteService) { }
  ngOnInit(): void {
    this.loadSales();
  }

  loadSales(): void {
    this.offertS.getSalesOffert().subscribe({
      next: (data) => {
        this.listeVentes = data;
      },
      error: (err) => {
        this.errroMessage = 'erreur dans le chargement des ventes';
        console.log(err);

      }
    })
  }
}
