import {Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginRequest} from './login-request.model';
import {Observable, tap} from 'rxjs';
import {LoginResponse} from './login-response.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = 'http://localhost:8000/api/auth';

  readonly rolle  = signal<string | null>(localStorage.getItem('rolle'));
  readonly name = signal<string | null>(localStorage.getItem('name'));

  constructor(private http: HttpClient) {}

  login(req: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, req).pipe(
      tap(res => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('rolle', res.rolle);
        localStorage.setItem('name', res.name);

        this.rolle.set(res.rolle);
        this.name.set(res.name);
      })
    )
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('rolle');
    localStorage.removeItem('name');

    this.rolle.set(null);
    this.name.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAngemeldet(): boolean {
    return this.getToken() !== null;
  }

  hasRolle(...rollen: string[]): boolean {
    return rollen.includes(this.rolle() ?? '');
  }
}
