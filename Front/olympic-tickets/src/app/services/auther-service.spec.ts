import { provideHttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';

import { AutherService } from './auther-service';

describe('AutherService', () => {
  let service: AutherService;
  let httpmock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;

  function createFakeToken(payload: any): string {
    const base64 = btoa(JSON.stringify(payload));
    return `aaa.${base64}.bbb`; 
  }

  beforeEach(() => {
    routerSpy = jasmine.createSpyObj("Router", ["navigate"]);

    TestBed.configureTestingModule({
      providers: [
        AutherService,
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: Router, useValue: routerSpy }
      ]
    });

    service = TestBed.inject(AutherService);
    httpmock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpmock.verify();
    localStorage.clear();
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  it("login devrait faire un POST et retourner un token", () => {
    const mockToken = { token: "fake-jwt-token" };

    service.login("test@gmail.com", "test").subscribe(res => {
      expect(res.token).toBe(mockToken.token);
    });

    const req = httpmock.expectOne(`${(service as any).baseUrl}/login`);
    expect(req.request.method).toBe("POST");
    req.flush(mockToken);
  });

  it("register devrait sauvegarder le token dans le localStorage", () => {
    const mockToken = { token: "fake-jwt-token" };

    service.register({ email: "test1@gmail.com", password: "test" }).subscribe(() => {
      expect(localStorage.getItem("token")).toBe(mockToken.token);
    });

    const req = httpmock.expectOne(`${(service as any).baseUrl}/register`);
    expect(req.request.method).toBe("POST");
    req.flush(mockToken);
  });

  it("logout devrait supprimer le token et rediriger vers /login", () => {
    localStorage.setItem("token", "abc");
    service.logout();
    expect(localStorage.getItem("token")).toBeNull();
    expect(routerSpy.navigate).toHaveBeenCalledWith(["/login"]);
  });

  it('getRole devrait extraire les rôles depuis le token', () => {
    const token = createFakeToken({ roles: ['USER', 'ADMIN'], exp: Math.floor(Date.now() / 1000) + 1000 });
    localStorage.setItem('token', token);
    expect(service.getRole()).toContain('ADMIN');
  });

  it('is_Admin devrait retourner true si is_admin = true', () => {
    const token = createFakeToken({ is_admin: true });
    localStorage.setItem('token', token);
    expect(service.is_Admin()).toBeTrue();
  });

  it('isTokenExpired devrait détecter un token expiré', () => {
    const token = createFakeToken({ exp: Math.floor(Date.now() / 1000) - 100 });
    localStorage.setItem('token', token);
    expect(service.isTokenExpired()).toBeTrue();
  });

  it('isTokenExpired devrait détecter un token valide', () => {
    const token = createFakeToken({ exp: Math.floor(Date.now() / 1000) + 1000 });
    localStorage.setItem('token', token);
    expect(service.isTokenExpired()).toBeFalse();
  });
});
