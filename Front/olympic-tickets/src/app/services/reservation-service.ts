import { Observable } from 'rxjs';
import { HttpClient, HttpContext, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseUrl = "http://localhost:8081/api/reservation";
  constructor(private http: HttpClient) { }

  createReservation(userId: number, offertId: number): Observable<any> {
    const params = new HttpParams()
    .set("userId", userId.toString())
    .set("offertId", offertId.toString());

    return this.http.post(`${this.baseUrl}`,null, { params });
  }

}
