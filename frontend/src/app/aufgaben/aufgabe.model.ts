import {BenutzerResponse} from '../benutzer/benutzer.model';

export interface AufgabeRequest {
  titel: string;
  beschreibung: string;
}

export interface AufgabeResponse {
  id: string;
  titel: string;
  beschreibung: string;
  status: AufgabeStatus;
  zugewiesenerBenutzer: BenutzerResponse | null;
}

export type AufgabeStatus = 'OFFEN' | 'IN_BEARBEITUNG' | 'ERLEDIGT';
