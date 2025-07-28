import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OffertModel } from '../model/OffertModel';


@Injectable({
  providedIn: 'root'
})
export class OfferteService {

  private ApiUrl ="http://localhost:8081/api/offert";
  constructor(private http: HttpClient) { }

  getOffert(): Observable<OffertModel[]> {
    return this.http.get<OffertModel[]>(this.ApiUrl);
  }
}
