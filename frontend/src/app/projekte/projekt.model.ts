export interface ProjektResponse {
  id: string;
  name: string;
  status: 'AKTIV' | 'ARCHIVIERT';
  fortschritt: number;
}

export interface ProjektRequest {
  name: string;
}

export interface ProjektStatusRequest {
  status: 'AKTIV' | 'ARCHIVIERT';
}
