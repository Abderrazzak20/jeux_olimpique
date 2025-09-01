import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Register } from './register';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('Register', () => {
  let component: Register;
  let fixture: ComponentFixture<Register>;
  let mockAuthService: jasmine.SpyObj<AutherService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockAuthService = jasmine.createSpyObj("AutherService", ["register"]);
    mockRouter = jasmine.createSpyObj("Router", ["navigate"]);

    await TestBed.configureTestingModule({
      imports: [Register, ReactiveFormsModule], // Import standalone component
      providers: [
        { provide: AutherService, useValue: mockAuthService }, // Corretto useValue
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Register);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait créer le composant', () => {
    expect(component).toBeTruthy();
  });

  it("devrait invalider le formulaire si les champs sont vides", () => {
    component.registerForm.patchValue({
      username: '',
      name: '',
      email: '',
      password: ''
    });
    expect(component.registerForm.invalid).toBeTrue();
  });

  it("devrait appeler register et naviguer si le formulaire est valide", () => {
    const user = {
      username: 'testuser',
      name: 'Test User',
      email: 'test@example.com',
      password: '123456',
      admin: false
    };

    mockAuthService.register.and.returnValue(of({}));
    component.registerForm.patchValue(user);
    component.onSubmit();

    expect(mockAuthService.register).toHaveBeenCalledWith(user);
    expect(mockRouter.navigate).toHaveBeenCalledWith(["/home"]);
    expect(component.errorMessage).toBe("");
  });

  it("devrait afficher un message d'erreur si le service échoue", () => {
    const user = {
      username: 'testuser',
      name: 'Test User',
      email: 'test@example.com',
      password: '123456',
      admin: false
    };

    const erreur = { error: { message: 'Erreur serveur' } };
    mockAuthService.register.and.returnValue(throwError(() => erreur));
    component.registerForm.patchValue(user);
    component.onSubmit();

    expect(component.errorMessage).toBe('Erreur serveur');
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });
});
