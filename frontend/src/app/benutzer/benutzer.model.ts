export interface BenutzerResponse {
  id: string;
  name: string;
  email: string;
  rolle: BenutzerRolle;
}

export interface BenutzerRequest {
  name: string;
  email: string;
  passwort: string;
  rolle: BenutzerRolle;
}

export type BenutzerRolle = 'ADMIN' | 'PROJEKTLEITER' | 'MITARBEITER' | 'BENUTZER';
