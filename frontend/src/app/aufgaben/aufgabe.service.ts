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

  statusAendern(projektId: string, aufgabeId: string, status: AufgabeStatus):Observable<AufgabeResponse> {
    return this.http.put<AufgabeResponse>(`${this.apiUrl}/${projektId}/aufgaben/${aufgabeId}/status`, {status});
  }
}
