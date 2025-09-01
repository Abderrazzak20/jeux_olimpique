import { ReservationService } from './../../services/reservation-service';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Reservation } from './reservation';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('Reservation', () => {
  let component: Reservation;
  let fixture: ComponentFixture<Reservation>;
  let mockReservationService: jasmine.SpyObj<ReservationService>;
  let mockAutherService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {

    mockReservationService = jasmine.createSpyObj("ReservationService", ["getUserReservationId"]);
    mockAutherService = jasmine.createSpyObj("AutherService", ["getUserId"]);
    mockRouter = jasmine.createSpyObj("Router", ["navigate"]);

    await TestBed.configureTestingModule({
      imports: [Reservation, CommonModule, FormsModule],
      providers: [
        { provide: ReservationService, useValue: mockReservationService },
        { provide: AutherService, useValue: mockAutherService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Reservation);
    component = fixture.componentInstance;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait charger les reservations avec succés ", () => {
    const donneMock = [
      { seats: 2, status: 'CONFIRMED', offert: { name: 'Offre A', location: 'Paris', date: new Date() } }
    ];
    mockAutherService.getUserId.and.returnValue(123);
    mockReservationService.getUserReservationId.and.returnValue(of(donneMock));
    component.ngOnInit();
    expect(component.isLoading).toBeFalse();
    expect(component.reservations).toEqual(donneMock);

  });
  it("devrait gerer une erruer lors du chargement des reservation", () => {
    spyOn(window, "alert");
    mockAutherService.getUserId.and.returnValue(123);
    mockReservationService.getUserReservationId.and.returnValue(throwError(() => new Error("error")));
    component.ngOnInit();

    expect(window.alert).toHaveBeenCalledWith("Erreur lors du chargement des réservations.");
    expect(component.isLoading).toBeFalse()
  });

});
