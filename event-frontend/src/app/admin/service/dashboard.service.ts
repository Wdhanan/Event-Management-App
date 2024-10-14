import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EventDto } from '../models/EventDto';
import { AuthService } from 'src/app/auth/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private baseUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllEvents(): Observable<EventDto[]> {
   
    return this.http.get<EventDto[]>(this.baseUrl, {
      headers: this.createAuthorizationHeader() // send the Token
    });

  }

  createEvent(event: EventDto): Observable<EventDto> {
    return this.http.post<EventDto>(this.baseUrl, event,{
      headers: this.createAuthorizationHeader() // send the Token
    });

  }

  updateEvent(id: number, event: EventDto): Observable<EventDto> {
    return this.http.put<EventDto>(`${this.baseUrl}/${id}`, event, {
      headers: this.createAuthorizationHeader() // send the Token
    });

  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    const token = this.authService.getToken();
    if (token) {
      return authHeaders.set('Authorization', 'Bearer ' + token);
    } else {
      console.error('Token not found!');
      return authHeaders;
    }
  }
}
