import { Observable } from 'rxjs';
import { HttpClient, HttpContext, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseUrl = "http://localhost:8081/api/reservation";
  constructor(private http: HttpClient) { }

  createReservation(userId: number, offertId: number, seat: number): Observable<any> {
    const params = new HttpParams()
    .set("userId", userId)
    .set("offertId", offertId)
    .set("seat",seat);

    return this.http.post(`${this.baseUrl}`,null, { params });
  }

  getUserReservationId(userId:number):Observable<any[]>{
    return this.http.get<any[]>(`${this.baseUrl}/user/${userId}`);
  }
}
