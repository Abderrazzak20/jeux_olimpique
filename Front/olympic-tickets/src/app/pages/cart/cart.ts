import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { CartItem, CartService } from '../../services/cart-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservationService } from '../../services/reservation-service';
import { AutherService } from '../../services/auther-service';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class Cart {
  cartItem: CartItem[] = []
  constructor(private router: Router, private cartS: CartService, private reservationS: ReservationService, private authS: AutherService) { }


  ngOnInit(): void {
    this.loadCart();
  }
  loadCart(): void {
    this.cartItem = this.cartS.getItem();
  }
  updateQuantity(item: CartItem, newQuantity: number): void {
    const maxQuantity = item.offert.availableSeats || 99;
    const validQuantity = Math.max(1, Math.min(newQuantity, maxQuantity));

    this.cartS.updateQuantity(item.offert.id, validQuantity);
    this.loadCart();
  }


  removeItem(item: CartItem): void {
    this.cartS.removeCarteItems(item.offert.id)
    this.loadCart();
  }
  clearCart(): void {
    this.cartS.clearCart();
    this.loadCart();
  }

  get total(): number {
    return this.cartItem.reduce((sum, item) => sum + item.offert.price * item.quantity, 0);
  }

  mockPayment(): void {
    const userId = this.authS.getUserId();
    if (!userId) {
      alert("Vous devez être connecté pour effectuer un paiement.");
      return;
    }

    let hasError = false;

    const reservation = this.cartItem.map(item => {
      if (item.quantity > item.offert.availableSeats) {
        alert(`Pas assez de places pour l’offre "${item.offert.name}".`);
        hasError = true;
        return null;
      }
      return this.reservationS.createReservation(userId, item.offert.id, item.quantity);
    }).filter(Boolean);

    if (!hasError && reservation.length > 0) {
      Promise.all(reservation.map(obs => obs!.toPromise())).then(() => {
        alert("Paiement réussi et réservations créées !");
        this.clearCart();
        this.router.navigate(["/reservation"]);
      })
        .catch(error => {
          alert("Une erreur est survenue pendant la réservation.");
          console.error(error);
        });
    }
  }

}
