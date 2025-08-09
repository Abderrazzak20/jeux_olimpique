import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OffertModel } from '../model/OffertModel';


@Injectable({
  providedIn: 'root'
})
export class OfferteService {

  private ApiUrl = "http://localhost:8081/api/offert";
  constructor(private http: HttpClient) { }

  getOffert(): Observable<OffertModel[]> {
    return this.http.get<OffertModel[]>(this.ApiUrl);
  }

  getOffertById(id: number): Observable<OffertModel> {

    return this.http.get<OffertModel>(this.ApiUrl + "/" + id);
  }

  createOffert(offert: OffertModel): Observable<any> {
    return this.http.post(`${this.ApiUrl}`, offert);
  }

  offertUpdate(id: number, offertData: any) {
    return this.http.put(`${this.ApiUrl}/${id}`, offertData);
  }
}
