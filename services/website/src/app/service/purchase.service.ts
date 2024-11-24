import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // Importa HttpClient
import { Observable } from 'rxjs'; // Importa Observable para manejar respuestas asíncronas
import { UserPurchase } from '../models/user-purchase.model';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  createPurchase(userId: string, userPurchase: UserPurchase[]): Observable<any> {
    const url = `${this.baseUrl}/${userId}/purchases`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post(url, userPurchase, { headers });
  }

}
