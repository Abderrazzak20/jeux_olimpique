import { ReactiveFormsModule } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertEdit } from './offert-edit';
import { OfferteService } from '../../services/offerte-service';
import { ActivatedRoute, Router } from '@angular/router';
import { Offerts } from '../offert/offert';
import { of, throwError } from 'rxjs';

describe('OffertEdit', () => {
  let component: OffertEdit;
  let fixture: ComponentFixture<OffertEdit>;
  let mockOffertService: jasmine.SpyObj<OfferteService>;
  let mockRouter: jasmine.SpyObj<Router>;

  const mockOffert = {
    id: 1,
    name: 'Offre UNO',
    price: 100,
    availableSeats: 5,
    max_People: 10,
    description: 'Description',
    location: 'Paris',
    date: '2025-01-01',
    imageUrl: '',
    deleted: false,
  };

  beforeEach(async () => {
    mockOffertService = jasmine.createSpyObj("offertService", ["getOffertById", "offertUpdate"]);
    mockRouter = jasmine.createSpyObj("Router", ["navigate"]);

    await TestBed.configureTestingModule({
      imports: [OffertEdit, ReactiveFormsModule],
      providers: [
        { provide: OfferteService, useValue: mockOffertService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: new Map([['id', '1']]) } } }

      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OffertEdit);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait charger l'offre au demarrage (ngOinit)", () => {
    mockOffertService.getOffertById.and.returnValues(of(mockOffert));
    fixture.detectChanges();

    expect(component.editForm.value.name).toBe(mockOffert.name);
    expect(component.editForm.value.price).toBe(mockOffert.price);
    expect(component.errorMessage).toBe("");
  });

  it("devrait appler offertUpdate et naviguer si le forumulaire est valide ", () => {
    mockOffertService.getOffertById.and.returnValue(of(mockOffert));
    fixture.detectChanges();

    mockOffertService.offertUpdate.and.returnValue(of({}));

    // Il form patchValue con tutti i campi tranne id e deleted
    component.editForm.patchValue({
      name: mockOffert.name,
      price: mockOffert.price,
      availableSeats: mockOffert.availableSeats,
      max_People: mockOffert.max_People,
      description: mockOffert.description,
      location: mockOffert.location,
      date: mockOffert.date,
      imageUrl: mockOffert.imageUrl
    });

    component.onSubmit();

    expect(mockOffertService.offertUpdate).toHaveBeenCalledWith(1, {
      name: mockOffert.name,
      price: mockOffert.price,
      availableSeats: mockOffert.availableSeats,
      max_People: mockOffert.max_People,
      description: mockOffert.description,
      location: mockOffert.location,
      date: mockOffert.date,
      imageUrl: mockOffert.imageUrl
    });
    expect(mockRouter.navigate).toHaveBeenCalledWith(["/offert"]);
  });


  it("ne devrait pas soummetre si le formulaire est invalide", () => {
    mockOffertService.getOffertById.and.returnValue(of(mockOffert));
    fixture.detectChanges();
    component.editForm.patchValue({ name: "", price: -1 });
    component.onSubmit();

    expect(component.errorMessage).toBe("Veuillez remplir tous les champs correctement.");
    expect(mockOffertService.offertUpdate).not.toHaveBeenCalled();
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });

  it("devrait afficher un message erreur si offertUpdate échoue ", () => {
    mockOffertService.getOffertById.and.returnValue(of(mockOffert));
    fixture.detectChanges();
    const errorResponse = { error: { message: "Erreur serveur" } };
    mockOffertService.offertUpdate.and.returnValue(throwError(() => errorResponse));
    component.editForm.patchValue(mockOffert);
    component.onSubmit();

    expect(component.errorMessage).toBe("Erreur serveur");
  });


});
