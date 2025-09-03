import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Cart } from './cart';
import { CartService, CartItem } from '../../services/cart-service';
import { ReservationService } from '../../services/reservation-service';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('Cart', () => {
  let component: Cart;
  let fixture: ComponentFixture<Cart>;
  let mockCartService: jasmine.SpyObj<CartService>;
  let mockReservationService: jasmine.SpyObj<ReservationService>;
  let mockAuthService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  const mockItem: CartItem = {
    offert: {
      id: 1,
      name: 'Offre A',
      price: 10,
      availableSeats: 5,
      max_People: 10,
      description: 'Description test',
      deleted: false
    },
    quantity: 2
  };

  beforeEach(async () => {
    mockCartService = jasmine.createSpyObj('CartService', ['getItem', 'updateQuantity', 'removeCarteItems', 'clearCart']);
    mockReservationService = jasmine.createSpyObj('ReservationService', ['createReservation']);
    mockAuthService = jasmine.createSpyObj('AutherService', ['getUserId']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    mockCartService.getItem.and.returnValue([mockItem]);

    await TestBed.configureTestingModule({
      imports: [Cart],
      providers: [
        { provide: CartService, useValue: mockCartService },
        { provide: ReservationService, useValue: mockReservationService },
        { provide: AutherService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Cart);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait créer le composant', () => {
    expect(component).toBeTruthy();
  });

  it('devrait charger les items du panier', () => {
    component.loadCart();
    expect(component.cartItem.length).toBe(1);
    expect(mockCartService.getItem).toHaveBeenCalled();
  });

  it('devrait mettre à jour la quantité', () => {
    component.updateQuantity(mockItem, 3);
    expect(mockCartService.updateQuantity).toHaveBeenCalledWith(1, 3);
  });

  it('devrait supprimer un item', () => {
    component.removeItem(mockItem);
    expect(mockCartService.removeCarteItems).toHaveBeenCalledWith(1);
  });

  it('devrait vider le panier', () => {
    component.clearCart();
    expect(mockCartService.clearCart).toHaveBeenCalled();
  });

  it('devrait calculer le total correctement', () => {
    component.loadCart();
    expect(component.total).toBe(20); 
  });
});
