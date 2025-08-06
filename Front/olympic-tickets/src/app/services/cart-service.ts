import { OffertModel } from './../model/OffertModel';
import { Injectable } from '@angular/core';

export interface CartItem {
  offert: OffertModel;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private storageKey = "cart";
  constructor() { }


  getItem(): CartItem[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];

  }

  saveItem(items: CartItem[]) {
    localStorage.setItem(this.storageKey, JSON.stringify(items));
  }

  addToCart(offert: OffertModel, quantity: number = 1) {
    const items = this.getItem();
    const index = items.findIndex(item => item.offert.id === offert.id);
    if (index > -1) {
      items[index].quantity += quantity;
    } else {
      items.push({ offert, quantity });
    }
    this.saveItem(items);
  }

  removeCarteItems(offertId: number) {
    let item = this.getItem();
    item = item.filter(item => item.offert.id !== offertId);
    this.saveItem(item)
  }
  clearCart() {
    localStorage.removeItem(this.storageKey);
  }

  updateQuantity(offertId: number, quantity: number) {
    const item = this.getItem();
    const index = item.findIndex(item => item.offert.id === offertId);
    if (index >= 0) {
      item[index].quantity = quantity;
      if (quantity <= 0) {
        item.splice(index, 1)
      }
      this.saveItem(item)
    }
  }
}
