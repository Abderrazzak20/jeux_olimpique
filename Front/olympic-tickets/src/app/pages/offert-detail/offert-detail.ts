import { CartService } from './../../services/cart-service';
import { OfferteService } from './../../services/offerte-service';
import { Component, OnInit } from '@angular/core';
import { OffertModel } from '../../model/OffertModel';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Offerts } from '../offert/offert';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-offert-detail',
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './offert-detail.html',
  styleUrl: './offert-detail.css'
})
export class OffertDetail implements OnInit {
  offertM: OffertModel | null = null;
  isLoading = true;
  quantity = 1;

  constructor(
    private route: ActivatedRoute,
    private OfferteS: OfferteService,
    private CartS: CartService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.OfferteS.getOffertById(id).subscribe({
      next: (data) => {
        this.offertM = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("erreur:", err);
        this.isLoading = false

      }
    })
  }
  addToCart() {
    if (this.offertM && this.quantity > 0) {
      this.CartS.addToCart(this.offertM, this.quantity);
      alert(`offre ajoute au panier (${this.quantity}places)`);
    }
  }
}
