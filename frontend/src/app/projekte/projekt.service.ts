import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProjektRequest, ProjektResponse} from './projekt.model';

@Injectable({providedIn: 'root'})
export class ProjektService {
  private readonly apiUrl = 'http://localhost:8080/api/projekte';
  private http = inject(HttpClient);

  getMeineProjekte(): Observable<ProjektResponse[]> {
    return this.http.get<ProjektResponse[]>(this.apiUrl);
  }

  getProjekt(id: string): Observable<ProjektResponse> {
    return this.http.get<ProjektResponse>(`${this.apiUrl}/${id}`);
  }

  projektAnlegen(req: ProjektRequest): Observable<ProjektResponse> {
    return this.http.post<ProjektResponse>(this.apiUrl, req);
  }

  statusAendern(id: string, status: 'AKTIV' | 'ARCHIVIERT'): Observable<ProjektResponse> {
    return this.http.patch<ProjektResponse>(`${this.apiUrl}/${id}/status`, {status});
  }

  mitarbeiterZuordnen(projektId: string, benutzerId: string): Observable<ProjektResponse> {
    return this.http.post<ProjektResponse>(`${this.apiUrl}/${projektId}/mitarbeiter/${benutzerId}`, {});
  }

  projektAktualisieren(id: string, req: ProjektRequest): Observable<ProjektResponse> {
    return this.http.put<ProjektResponse>(`${this.apiUrl}/${id}`, req);
  }

  mitarbeiterEntfernen(projektId: string, benutzerId: string): Observable<ProjektResponse> {
    return this.http.delete<ProjektResponse>(`${this.apiUrl}/${projektId}/mitarbeiter/${benutzerId}`);
  }
}
