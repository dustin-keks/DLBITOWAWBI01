export interface ProjektResponse {
  id: string;
  name: string;
  status: 'AKTIV' | 'ARCHIVIERT';
  fortschritt: number;
}
