import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Navbar } from './navbar';
import { AutherService } from '../../app/services/auther-service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
describe('Navbar', () => {
  let component: Navbar;
  let fixture: ComponentFixture<Navbar>;
  let mockAutherService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockAutherService = jasmine.createSpyObj("AutherService", ['isLoggin', 'getToken', 'logout']);
    mockRouter = jasmine.createSpyObj("Router", ["navigare"]);

    await TestBed.configureTestingModule({
      imports: [Navbar],
      providers: [
        { provide: AutherService, useValue: mockAutherService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: { params: of({}) } }
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(Navbar);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait retourner true si l’utilisateur est connecté", () => {
    mockAutherService.isLoggin.and.returnValue(true);
    expect(component.isLoggedIn()).toBeTrue();
  });

  it("devrait basculer l’état du menu avec toggleMenu", () => {
    expect(component.menuOpen).toBeFalse();
    component.toggleMenu();
    expect(component.menuOpen).toBeTrue();
  });
  it("devrait appeler logout du service Authentification", () => {
    component.logout();
    expect(mockAutherService.logout).toHaveBeenCalled();
  });
  it("devrait retourner le nom utilisateur à partir du token", () => {
    const payload = { sub: "user@gmail.com" };
    const token = `header.${btoa(JSON.stringify(payload))}.signature`;
    mockAutherService.getToken.and.returnValue(token),
      expect(component.getUserEmail()).toEqual("user");
  });


});
