import { OffertModel } from './../model/OffertModel';
import { TestBed } from '@angular/core/testing';

import { CartService, CartItem } from './cart-service';


describe('CartService', () => {
  let service: CartService;

  const mockOffert: OffertModel = {
    id: 1,
    name: 'Test Offert',
    price: 100,
    max_People: 5,
    availableSeats: 5,
    description: 'Description test',
    deleted: false,
  };


  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartService);
    localStorage.clear();
  });

  afterEach(() => {
    localStorage.clear();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("devrait retourner un tableau vide si le panier est vide", () => {
    expect(service.getItem()).toEqual([]);
  });
  it("devrait sauvegarder et récupérer les articles du localStorage", () => {
    const items: CartItem[] = [{ offert: mockOffert, quantity: 2 }];
    service.saveItem(items)

    const stored = service.getItem();
    expect(stored.length).toBe(1);
    expect(stored[0].offert.name).toBe("Test Offert");
    expect(stored[0].quantity).toBe(2)
  })

  it("devrait ajouter un nouvel article au panier", () => {
    service.addToCart(mockOffert, 3);
    const stored = service.getItem();
    expect(stored.length).toBe(1);
    expect(stored[0].quantity).toBe(3)
  })
  it("devrait supprimer un article par son id ", () => {
    service.addToCart(mockOffert, 5);
    service.removeCarteItems(mockOffert.id);
    const stored = service.getItem();
    expect(stored.length).toBe(0)
  })
  it("devrai augmenter la quantité si l'article est deja dans le panier", () => {
    service.addToCart(mockOffert, 1);
    service.addToCart(mockOffert, 2);
    const stored = service.getItem();
    expect(stored[0].quantity).toBe(3);
  });
  it("devrait vider le panier", () => {
    service.addToCart(mockOffert, 10);
    service.clearCart();
    expect(service.getItem()).toEqual([])
  });

  it("devrait mettre a jour la quanité de l'article", () => {
    service.addToCart(mockOffert, 1),
      service.updateQuantity(mockOffert.id, 4);
    const stored = service.getItem();
    expect(stored[0].quantity).toBe(4);
  });


  it("devrait supprimer article si la quantité devient 0", () => {
    service.addToCart(mockOffert, 10);
    service.updateQuantity(mockOffert.id, 0);
    expect(service.getItem().length).toBe(0);
  });

});
