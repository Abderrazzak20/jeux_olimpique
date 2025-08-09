import { OfferteService } from './../../services/offerte-service';
import { OffertModel } from './../../model/OffertModel';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AutherService } from '../../services/auther-service';

@Component({
  selector: 'app-offert',
  imports: [CommonModule, RouterLink],
  templateUrl: './offert.html',
  styleUrl: './offert.css'
})
export class Offerts implements OnInit {
  offertlist: OffertModel[] = [];
  isLoading: boolean = true;
  isAdmin: boolean = false;

  constructor(private OfferteService: OfferteService, private authS: AutherService, private routes: Router) { }


  ngOnInit(): void {
    this.isAdmin = this.authS.is_Admin();

    console.log("Offerts ngOnInit: chiamo getOffert");
    this.OfferteService.getOffert().subscribe({
      next: (data) => {
        console.log("Dati ricevuti:", data);
        this.offertlist = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des offres :", err);
        this.isLoading = false;
      }
    });
  }

  onCreateOffer(): void {
    this.routes.navigate(["/offert/create"]);
  }

  onEditfOffert(id: number): void {
    this.routes.navigate(["/offert/edit",id]);
  }

}
