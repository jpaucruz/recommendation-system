import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { UserAction } from '../models/user-action.model';

@Injectable({
  providedIn: 'root'
})
export class WebactionsService {

  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  sendUserAction(userId: string, userAction: UserAction): Observable<any> {
    const url = `${this.baseUrl}/${userId}/actions`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post(url, userAction, { headers });
  }

}
