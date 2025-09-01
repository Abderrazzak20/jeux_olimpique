import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';

import { OfferteService } from './offerte-service';
import { OffertModel } from '../model/OffertModel';
import { SalesDTO } from '../model/SalesDTO';

describe('OfferteService', () => {
  let service: OfferteService;
  let httpMock: HttpTestingController;
  const apiUrl = "https://jeuxolimpique-jo2025back.up.railway.app/api/offert";

  const mockOffert: OffertModel = {
    id: 1,
    name: 'Test Offert',
    price: 100,
    max_People: 5,
    availableSeats: 5,
    description: 'Description test',
    deleted: false,
  };

  const mockSales: SalesDTO[] = [
    { offertId: 1, offertName: "Offre Test", totalSeatsSold: 50 }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        OfferteService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(OfferteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('devrait être créé', () => {
    expect(service).toBeTruthy();
  });

  it("devrait récupérer les offres actives", () => {
    service.getActiveOffert().subscribe(res => {
      expect(res.length).toBe(1);
      expect(res[0].id).toBe(1);
    });

    const req = httpMock.expectOne(`${apiUrl}/active`);
    expect(req.request.method).toBe("GET");
    req.flush([mockOffert]);
  });

  it("devrait récupérer une offre par id", () => {
    service.getOffertById(1).subscribe(res => {
      expect(res.id).toBe(1);
      expect(res.name).toBe("Test Offert");
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe("GET");
    req.flush(mockOffert);
  });

  it("devrait créer une nouvelle offre", () => {
    service.createOffert(mockOffert).subscribe(res => {
      expect(res).toEqual(mockOffert);
    });

    const req = httpMock.expectOne(`${apiUrl}`);
    expect(req.request.method).toBe("POST");
    req.flush(mockOffert);
  });

  it("devrait mettre à jour une offre", () => {
    const data = { name: "modifié" };

    service.offertUpdate(1, data).subscribe(res => {
      expect(res).toEqual(data);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe("PUT");
    req.flush(data);
  });

  it("devrait supprimer une offre", () => {
    service.deleteOffert(1).subscribe(res => {
      expect(res).toBeNull();
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe("DELETE");
    req.flush(null);
  });

  it("devrait restaurer une offre", () => {
    service.restoreOffert(1).subscribe(res => {
      expect(res).toEqual({ success: true });
    });

    const req = httpMock.expectOne(`${apiUrl}/restore/1`);
    expect(req.request.method).toBe("PUT");
    req.flush({ success: true });
  });

  it("devrait récupérer les ventes d'offres", () => {
    service.getSalesOffert().subscribe(res => {
      expect(res).toEqual(mockSales);
      expect(res[0].totalSeatsSold).toBe(50);
    });

    const req = httpMock.expectOne(`${apiUrl}/sales`);
    expect(req.request.method).toBe("GET");
    req.flush(mockSales);
  });

});
