import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { SignupRequestDTO } from '../models/SignupRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  signup(user: SignupRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/normal-participant/sign-up`, user);
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/authenticate`, { username, password }, {
      observe: 'response' // Die gesamte HTTP-Antwort inkl. Header zurückgeben
    }).pipe(
      map(response => {
        const authHeader = response.headers.get('Authorization'); // Authorization Header lesen
        if (authHeader) {
          const token = authHeader.substring(7); // "Bearer " entfernen
          this.setToken(token); // Token im Local Storage speichern
          console.log('Token:', token);
        }
        return response.body; // Body der Antwort zurückgeben, falls nötig
      })
    );
  }
  

  // Méthode pour stocker le token dans le local storage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Méthode pour récupérer le token
  static getToken(): string | null {
    return localStorage.getItem('token');
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Méthode pour supprimer le token
  removeToken(): void {
    localStorage.removeItem('token');
  }

  // Méthode pour vérifier si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    const token = this.getToken();
    return token !== null; // Vérifie simplement si le token existe
  }
}

