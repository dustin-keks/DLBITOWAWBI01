import {BenutzerResponse} from '../benutzer/benutzer.model';

export interface ProjektResponse {
  id: string;
  name: string;
  status: 'AKTIV' | 'ARCHIVIERT';
  fortschritt: number;
  mitarbeitende: BenutzerResponse[];
}

export interface ProjektRequest {
  name: string;
}

export interface ProjektStatusRequest {
  status: 'AKTIV' | 'ARCHIVIERT';
}
