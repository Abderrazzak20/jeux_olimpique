import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { Offerts } from './offert';
import { OfferteService } from '../../services/offerte-service';
import { AutherService } from '../../services/auther-service';
import { OffertModel } from '../../model/OffertModel';

describe('Offerts Component', () => {
  let component: Offerts;
  let fixture: ComponentFixture<Offerts>;
  let mockOfferteService: jasmine.SpyObj<OfferteService>;
  let mockAuthService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  const mockOfferts: OffertModel[] = [
    { id: 1, name: "Offre 1", price: 100, max_People: 5, availableSeats: 5, description: "desc", deleted: false },
    { id: 2, name: "Offre 2", price: 200, max_People: 10, availableSeats: 10, description: "desc", deleted: true }
  ];

  beforeEach(async () => {
    mockOfferteService = jasmine.createSpyObj('OfferteService', [
      'getAllOffert',
      'getActiveOffert',
      'deleteOffert',
      'restoreOffert'
    ]);
    mockAuthService = jasmine.createSpyObj('AutherService', ['is_Admin']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [Offerts],
      providers: [
        { provide: OfferteService, useValue: mockOfferteService },
        { provide: AutherService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Offerts);
    component = fixture.componentInstance;
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should load all offerts if admin', () => {
    mockAuthService.is_Admin.and.returnValue(true);
    mockOfferteService.getAllOffert.and.returnValue(of(mockOfferts));

    component.ngOnInit();

    expect(mockOfferteService.getAllOffert).toHaveBeenCalled();
    expect(component.offertlist.length).toBe(2);
    expect(component.isLoading).toBeFalse();
  });

  it('should load only active offerts if not admin', () => {
    mockAuthService.is_Admin.and.returnValue(false);
    mockOfferteService.getActiveOffert.and.returnValue(of([mockOfferts[0]]));

    component.ngOnInit();

    expect(mockOfferteService.getActiveOffert).toHaveBeenCalled();
    expect(component.offertlist.length).toBe(1);
    expect(component.isLoading).toBeFalse();
  });

  it('should navigate to create page on onCreateOffer', () => {
    component.onCreateOffer();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/offert/create']);
  });

  it('should navigate to edit page on onEditfOffert', () => {
    component.onEditfOffert(1);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/offert/edit', 1]);
  });

  it('should delete offert and mark it deleted', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    mockOfferteService.deleteOffert.and.returnValue(of(void 0));
    component.offertlist = [...mockOfferts];

    component.onRemoveOffert(1);

    expect(mockOfferteService.deleteOffert).toHaveBeenCalledWith(1);
    expect(component.offertlist.find(o => o.id === 1)?.deleted).toBeTrue();
  });

  it('should restore offert and mark it as not deleted', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    mockOfferteService.restoreOffert.and.returnValue(of({}));
    component.offertlist = [...mockOfferts];

    component.onRestoreOffert(2);

    expect(mockOfferteService.restoreOffert).toHaveBeenCalledWith(2);
    expect(component.offertlist.find(o => o.id === 2)?.deleted).toBeFalse();
  });

  it('should handle error when loading fails', () => {
    mockAuthService.is_Admin.and.returnValue(true);
    mockOfferteService.getAllOffert.and.returnValue(throwError(() => new Error('error')));

    component.ngOnInit();

    expect(component.isLoading).toBeFalse();
    expect(component.offertlist.length).toBe(0);
  });
});
