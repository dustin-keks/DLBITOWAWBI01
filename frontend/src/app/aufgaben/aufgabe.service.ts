import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AufgabeRequest, AufgabeResponse, AufgabeStatus} from './aufgabe.model';

@Injectable({providedIn: 'root'})
export class AufgabeService {
  private readonly apiUrl = 'http://localhost:8080/api/projekte';
  private http = inject(HttpClient);

  getAufgaben(projektId: string):Observable<AufgabeResponse[]> {
    return this.http.get<AufgabeResponse[]>(`${this.apiUrl}/${projektId}/aufgaben`);
  }

  aufgabeAnlegen(projektId: string, request: AufgabeRequest): Observable<AufgabeResponse> {
    return this.http.post<AufgabeResponse>(`${this.apiUrl}/${projektId}/aufgaben`, request);
  }

  aufgabeAktualisieren(projektId: string, aufgabeId: string, request: AufgabeRequest): Observable<AufgabeResponse> {
    return this.http.put<AufgabeResponse>(`${this.apiUrl}/${projektId}/aufgaben/${aufgabeId}`, request);
  }

  statusAendern(projektId: string, aufgabeId: string, status: AufgabeStatus):Observable<AufgabeResponse> {
    return this.http.put<AufgabeResponse>(`${this.apiUrl}/${projektId}/aufgaben/${aufgabeId}/status`, {status});
  }

  aufgabeLoeschen(projektId: string, aufgabeId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${projektId}/aufgaben/${aufgabeId}`);
  }

  aufgabeZuweisen(projektId: string, aufgabeId: string, benutzerId: string | null): Observable<AufgabeResponse> {
    return this.http.put<AufgabeResponse>(`${this.apiUrl}/${projektId}/aufgaben/${aufgabeId}/zuweisung`, {benutzerId});
  }
}
