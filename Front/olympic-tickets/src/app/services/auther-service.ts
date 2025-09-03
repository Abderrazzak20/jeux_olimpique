import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry, tap } from 'rxjs';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { Router } from '@angular/router';

interface tokenPaylod {
  sub: string;
  roles: string[];
  is_admin: true,
  id: number;
  iat: number;
  exp: number;
}
@Injectable({
  providedIn: 'root'
})
export class AutherService {
  private baseUrl = "https://jeuxolimpique-jo2025back.up.railway.app/auth";



  constructor(private http: HttpClient, private router: Router) { }
  login(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, {
      email,
      password
    });
  }


  register(user: any): Observable<any> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/register`, user).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token); 
        }
      })
    );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
     this.router.navigate(["/login"]);
  }

  isLoggin(): boolean {
    return !!localStorage.getItem('token');
  }

  getUserId(): number | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      const decode = jwtDecode<tokenPaylod>(token);
      return decode.id;
    } catch (error) {
      console.error("erreur dans le decode", error);
      return null;
    }
  }

  getRole(): string[] {
    const token = this.getToken();
    if (!token) return [];
    try {
      const decode = jwtDecode<{ roles: string[] }>(token);
      return decode.roles || null;
    } catch (error) {
      console.error("Erreur lors du décodage des rôle", error);
      return [];

    }
  }
  is_Admin(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      if (payload.is_admin == true) {
        console.log("dove seiiii", payload);

        return true;
      }
      if (payload.roles && Array.isArray(payload.roles)) {
        console.log("non ci  seiiii", payload);
        return payload.roles.includes('ADMIN');

      }
      return false;
    } catch {
      return false;
    }
  }

  isTokenExpired(token?: string | null): boolean {
    if (!token) {
      token = this.getToken();
      if (!token) return true;
    }
    try {
      const decode: any = jwtDecode(token);
      const maintenant = Math.floor(Date.now() / 1000);
      return decode.exp < maintenant;
    } catch (error) {
      console.error("Erreur lors de decodage de token", error);
      return true;
    }
  }

}
