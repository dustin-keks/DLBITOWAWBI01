export interface AufgabeRequest {
  titel: string;
  beschreibung: string;
}

export interface AufgabeResponse {
  id: string;
  titel: string;
  beschreibung: string;
  status: AufgabeStatus;
}

export type AufgabeStatus = 'OFFEN' | 'IN_BEARBEITUNG' | 'ERLEDIGT';
