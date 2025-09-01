import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ReservationService } from './reservation-service';
import { provideHttpClient } from '@angular/common/http';

describe('ReservationService', () => {
  let service: ReservationService;
  let httpMock: HttpTestingController;
  const baseUrl = "https://jeuxolimpique-jo2025back.up.railway.app/api/reservation";

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReservationService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(ReservationService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  afterEach(() => {
    httpMock.verify();
  })
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("devrait crée une reservation ", () => {
    const mockResponse = { succes: true };
    service.createReservation(1, 10, 3).subscribe(res => {
      expect(res).toEqual(mockResponse);
    })
    const req = httpMock.expectOne(
      re => re.url === baseUrl && re.method === "POST"
    )

    expect(req.request.params.get("userId")).toBe("1");
    expect(req.request.params.get("offertId")).toBe("10");
    expect(req.request.params.get("seat")).toBe("3");
    req.flush(mockResponse);
  });

  it("devrait recuper les reservtaions de un cliente", () => {
    const mockReservation = [
      { id: 1, userId: 1, offertId: 10, seat: 2 },
      { id: 2, userId: 1, offertId: 11, seat: 1 }
    ];
    service.getUserReservationId(1).subscribe(res => {
      expect(res.length).toBe(2);
      expect(res[0].offertId).toBe(10);
    })
    const req = httpMock.expectOne(`${baseUrl}/user/1`);
    expect(req.request.method).toBe("GET");
    req.flush(mockReservation);
  });

});
