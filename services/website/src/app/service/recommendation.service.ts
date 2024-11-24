import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Importa HttpClient
import { Observable } from 'rxjs'; // Importa Observable para manejar respuestas asíncronas

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  getRecommendations(userId: string): Observable<any[]> {
    const url = `${this.baseUrl}/${userId}/recommendations`;
    return this.http.get<any[]>(url);
  }

}
