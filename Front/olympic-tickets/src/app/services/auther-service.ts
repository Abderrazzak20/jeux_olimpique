import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode, JwtPayload } from 'jwt-decode';

interface tokenPaylod {
  sub: string;
  roles: string[];
  id: number;
  iat: number;
  exp: number;
}
@Injectable({
  providedIn: 'root'
})
export class AutherService {
  private baseUrl = "http://localhost:8081/auth";



  constructor(private http: HttpClient) { }
  login(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, {
      email,
      password
    });
  }


  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
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
}
