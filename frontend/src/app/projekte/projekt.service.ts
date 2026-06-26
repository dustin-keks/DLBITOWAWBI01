import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProjektResponse} from './projekt.model';

@Injectable({providedIn: 'root'})
export class ProjektService {
  private readonly apiUrl = 'http://localhost:8080/api/projekte';
  private http = inject(HttpClient);

  getMeineProjekte(): Observable<ProjektResponse[]> {
    return this.http.get<ProjektResponse[]>(this.apiUrl)
  }
}
