import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertSales } from './offert-sales';
import { OfferteService } from '../../services/offerte-service';
import { SalesDTO } from '../../model/SalesDTO';
import { of, throwError } from 'rxjs';

describe('OffertSales', () => {
  let component: OffertSales;
  let fixture: ComponentFixture<OffertSales>;
  let mockOfferteService: jasmine.SpyObj<OfferteService>;

  const ventesMock: SalesDTO[] = [
    { offertId: 1, offertName: "offre 1", totalSeatsSold: 5 },
    { offertId: 2, offertName: "offre 2", totalSeatsSold: 3 },
  ];

  beforeEach(async () => {
    mockOfferteService = jasmine.createSpyObj("OffertService", ["getSalesOffert"]);
    await TestBed.configureTestingModule({
      imports: [OffertSales],
      providers: [
        { provide: OfferteService, useValue: mockOfferteService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OffertSales);
    component = fixture.componentInstance;
  
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait charger les ventes ", () => {
    mockOfferteService.getSalesOffert.and.returnValue(of(ventesMock));
    fixture.detectChanges();

    expect(component.listeVentes).toEqual(ventesMock);
    expect(component.errroMessage).toBeNull();
    expect(mockOfferteService.getSalesOffert).toHaveBeenCalled();
  });

  it("devrait gérer l'erreur  du changement de ventes", () => {
    const erreur = { status: 500, message: "Erreur serveur" };
    mockOfferteService.getSalesOffert.and.returnValue(throwError(() => erreur));

    fixture.detectChanges();

    expect(component.listeVentes).toEqual([]);
    expect(component.errroMessage).toBe("erreur dans le chargement des ventes");
    expect(mockOfferteService.getSalesOffert).toHaveBeenCalled();
  });


});
