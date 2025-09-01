import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login } from './login';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('Login', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;
  let mockAutheService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockAutheService = jasmine.createSpyObj("AutherService", ["login", "saveToken"]);
    mockRouter = jasmine.createSpyObj("Router", ["navigate"]),
      await TestBed.configureTestingModule({
        imports: [Login],
        providers: [
          { provide: AutherService, useValue: mockAutheService },
          { provide: Router, useValue: mockRouter },
        ]
      }).compileComponents();

    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("devrait appeler login et naviguer sur /home en cas de succès", () => {
    const fakeToken = { "token": "testToken" };
    component.email = "test@gmail.com";
    component.password = "test";
    mockAutheService.login.and.returnValue(of(fakeToken));
    component.onLogin();
    expect(mockAutheService.login).toHaveBeenCalledWith("test@gmail.com", "test");
    expect(mockAutheService.saveToken).toHaveBeenCalledWith("testToken");
    expect(mockRouter.navigate).toHaveBeenCalledWith(["/home"])
  });

  it("devrait afficher message d'erreur en cas d'erreur", () => {
    component.email = 'test@example.com';
    component.password = 'wrong';
    mockAutheService.login.and.returnValue(throwError(() => new Error("wrong")));
    component.onLogin();
    expect(component.errorMessage).toEqual("Identifiants incorrects ou erreur serveur");
  });

});
