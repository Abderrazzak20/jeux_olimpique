import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

import { OffertCreate } from './offert-create';
import { OfferteService } from '../../services/offerte-service';
import { __param } from 'tslib';

describe('OffertCreate', () => {
  let component: OffertCreate;
  let fixture: ComponentFixture<OffertCreate>;
  let mockOfferteService: jasmine.SpyObj<OfferteService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockOfferteService = jasmine.createSpyObj("OfferteService", ["createOffert"]);
    mockRouter = jasmine.createSpyObj("Router", ["navigate"]);

    await TestBed.configureTestingModule({
      imports: [OffertCreate, ReactiveFormsModule, FormsModule],
      providers: [
        { provide: OfferteService, useValue: mockOfferteService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: { params: of({}) } }


      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OffertCreate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait créer le composant', () => {
    expect(component).toBeTruthy();
  });

  it("le formulaire devrait être invalide quand il est vide", () => {
    expect(component.createForm.valid).toBeFalse();
  });

  it("devrait afficher un message d'erreur si le formulaire est invalide lors du submit", () => {
    component.createForm.patchValue({ name: '' });
    component.onSubmit();
    expect(component.errorMessage).toBe("il faut remplir tous les champs");
    expect(mockOfferteService.createOffert).not.toHaveBeenCalled();
  });

  it("devrait appeler createOffert et naviguer en cas de succès", () => {
    component.createForm.setValue({
      name: "Offre test",
      price: 100,
      availableSeats: 5,
      max_People: 10,
      description: "description test",
      location: "Paris",
      date: "2025-01-01",
      imageUrl: ""
    });

    mockOfferteService.createOffert.and.returnValue(of({}));

    component.onSubmit();

    expect(mockOfferteService.createOffert).toHaveBeenCalledWith(component.createForm.value);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/offert']);
  });

  it("devrait afficher un message d'erreur si createOffert échoue", () => {
    component.createForm.setValue({
      name: "Offre test",
      price: 100,
      availableSeats: 5,
      max_People: 10,
      description: "description test",
      location: "Paris",
      date: "2025-01-01",
      imageUrl: ""
    });

    mockOfferteService.createOffert.and.returnValue(
      throwError(() => ({ error: { message: "Erreur de backend" } }))
    );

    component.onSubmit();

    expect(component.errorMessage).toBe("Erreur de backend");
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });

});
