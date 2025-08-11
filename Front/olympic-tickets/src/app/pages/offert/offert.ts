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
    this.loadOfferts();
  }

  private loadOfferts(): void {
    if (this.isAdmin) {
      this.loadAllOfferts();
    } else {
      this.loadActiveOfferts();
    }
  }

  private loadAllOfferts(): void {
    this.OfferteService.getAllOffert().subscribe({
      next: data => {
        this.offertlist = data;
        this.isLoading = false;
      },
      error: err => {
        console.error("Erreur lors du chargement des offres :", err);
        this.isLoading = false;
      }
    });
  }

  private loadActiveOfferts(): void {
    this.OfferteService.getActiveOffert().subscribe({
      next: data => {
        this.offertlist = data;
        this.isLoading = false;
      },
      error: err => {
        console.error("Erreur lors du chargement des offres :", err);
        this.isLoading = false;
      }
    });
  }



  onCreateOffer(): void {
    this.routes.navigate(["/offert/create"]);
  }

  onEditfOffert(id: number): void {
    this.routes.navigate(["/offert/edit", id]);
  }


  onRemoveOffert(id: number): void {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette offre ?")) {
      this.OfferteService.deleteOffert(id).subscribe({
        next: () => {
          this.offertlist = this.offertlist.filter(offre => offre.id !== id);
          alert("Offre supprimée avec succès");
        },
        error: (err) => {
          console.error("Erreur lors de la suppression de l'offre :", err);
          alert("Impossible de supprimer l'offre. Réessayez plus tard.");
        }
      })
    }
  }
onRestoreOffert(id: number): void {
  if (confirm("Êtes-vous sûr de vouloir réintégrer cette offre ?")) {
    this.OfferteService.restoreOffert(id).subscribe({
      next: () => {
        alert("Offre réintégrée avec succès");
        // Aggiorna la lista ricaricandola oppure modifica localmente l'elemento
        this.ngOnInit(); // oppure aggiorna solo quell’offerta in elenco
      },
      error: (err) => {
        console.error("Erreur lors de la réintégration de l'offre :", err);
        alert("Impossible de réintégrer l'offre. Réessayez plus tard.");
      }
    });
  }
}



}
