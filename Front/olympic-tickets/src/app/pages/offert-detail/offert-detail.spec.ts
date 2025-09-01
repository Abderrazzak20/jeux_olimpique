import { OffertModel } from './../../model/OffertModel';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertDetail } from './offert-detail';
import { OfferteService } from '../../services/offerte-service';
import { CartService } from '../../services/cart-service';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('OffertDetail', () => {
  let component: OffertDetail;
  let fixture: ComponentFixture<OffertDetail>;
  let mockOfferteService: jasmine.SpyObj<OfferteService>;
  let mockCartService: jasmine.SpyObj<CartService>;

  const mockOffert: OffertModel = {
    id: 1,
    name: "Offre UNO",
    price: 100,
    availableSeats: 5,
    max_People: 10,
    description: "Description ",
    location: "Paris",
    date: "2025-01-01",
    imageUrl: "",
    deleted: false,
  };

beforeEach(async () => {
  mockOfferteService = jasmine.createSpyObj("OfferteService", ["getOffertById"]);
  mockCartService = jasmine.createSpyObj("CartService", ["addToCart"]);

  await TestBed.configureTestingModule({
    imports: [OffertDetail],
    providers: [
      { provide: OfferteService, useValue: mockOfferteService },
      { provide: CartService, useValue: mockCartService },
      { provide: ActivatedRoute, useValue: { snapshot: { paramMap: new Map([["id", "1"]]) } } },
    ],
  }).compileComponents();

  fixture = TestBed.createComponent(OffertDetail);
  component = fixture.componentInstance;

  
  mockOfferteService.getOffertById.and.returnValue(of(mockOffert));

  fixture.detectChanges(); 
});
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait changer une offre au demarrage (ngini)", () => {
    mockOfferteService.getOffertById.and.returnValue(of(mockOffert));
    fixture.detectChanges();

    expect(component.offertM).toEqual(mockOffert);
    expect(component.isLoading).toBeFalse();
  });

  it("devrait gérer une erreru lors du changement d'une offre", () => {
    spyOn(console, "error");
    mockOfferteService.getOffertById.and.returnValue(throwError(() => new Error("Erreur test")));
    fixture.detectChanges();
  });
  it("devrait ajouter une offre au panier si la quantité est validé", () => {
    mockOfferteService.getOffertById.and.returnValue(of(mockOffert));
    fixture.detectChanges();
    component.quantity = 2;
    spyOn(window, "alert");
    component.addToCart();

    expect(mockCartService.addToCart).toHaveBeenCalledWith(mockOffert, 2);
    expect(window.alert).toHaveBeenCalledWith("offre ajoute au panier (2places)");
  });



});
