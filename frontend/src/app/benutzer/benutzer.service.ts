import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BenutzerRequest, BenutzerResponse} from './benutzer.model';

@Injectable({providedIn: 'root'})
export class BenutzerService {
  private readonly apiUrl = 'http://localhost:8080/api/benutzer';

  private http = inject(HttpClient);

  getAlleBenutzer(): Observable<BenutzerResponse[]> {
    return this.http.get<BenutzerResponse[]>(this.apiUrl);
  }

  benutzerAnlegen(req: BenutzerRequest):Observable<BenutzerResponse> {
    return this.http.post<BenutzerResponse>(this.apiUrl, req);
  }

  benutzerAktualisieren(id: string, req: BenutzerRequest):Observable<BenutzerResponse> {
    return this.http.put<BenutzerResponse>(`${this.apiUrl}/${id}`, req);
  }
}
