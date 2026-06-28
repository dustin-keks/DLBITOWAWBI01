export interface AufgabeRequest {
  titel: string;
}

export interface AufgabeResponse {
  id: string;
  titel: string;
  status: AufgabeStatus;
}

export type AufgabeStatus = 'OFFEN' | 'IN_BEARBEITUNG' | 'ERLEDIGT';
