import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideHttpClient} from '@angular/common/http';
import {AuthService} from './auth.service';
import {LoginResponse} from './login-response.model';

describe('AuthService', () => {
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.resetTestingModule();
    localStorage.clear();

    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it ('isAngemeldet() liefert true, wenn ein Token im localStorage liegt', () => {
    localStorage.setItem('token', 'abcdef');

    const authService = TestBed.inject(AuthService);

    expect(authService.isAngemeldet()).toBe(true);
  });

  it ('isAngemeldet() liefert false, wenn kein Token im localStorage liegt', () => {
    const authService = TestBed.inject(AuthService);

    expect(authService.isAngemeldet()).toBe(false);
  });

  it ('hasRolle() prüft, ob die aktuelle Rolle in der übergebenen Liste enthalten ist', () => {
    localStorage.setItem('rolle', 'MITARBEITER');

    const authService = TestBed.inject(AuthService);

    expect(authService.hasRolle('PROJEKTLEITER')).toBe(false);
    expect(authService.hasRolle('MITARBEITER')).toBe(true);
  });

  it ('login() speichert Token, Rolle, Name und aktualisiert die Signals', () => {
    const authService = TestBed.inject(AuthService);
    const response: LoginResponse = {
      token: 'abc123',
      rolle: 'ADMIN',
      name: 'Max Mustermann'
    };

    authService.login({
      email: 'max@hallo-welt.de',
      passwort: 'ein-Passwort'
    }).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(response);

    expect(localStorage.getItem('token')).toBe('abc123');
    expect(localStorage.getItem('rolle')).toBe('ADMIN');
    expect(localStorage.getItem('name')).toBe('Max Mustermann');
  });

  it ('logout() entfernt die gespeicherten Daten und setzt die Signals zurück', () => {
    localStorage.setItem('token', '999')
    localStorage.setItem('rolle', 'PROJEKTLEITER');
    localStorage.setItem('name', 'PO');

    const authService = TestBed.inject(AuthService);

    expect(authService.getToken()).toBe('999');
    expect(authService.rolle()).toBe('PROJEKTLEITER');
    expect(authService.name()).toBe('PO');

    authService.logout();

    expect(localStorage.getItem('token')).toBeNull();
    expect(localStorage.getItem('rolle')).toBeNull();
    expect(localStorage.getItem('name')).toBeNull();

    expect(authService.getToken()).toBeNull();
    expect(authService.rolle()).toBeNull();
    expect(authService.name()).toBeNull();
  });
});
