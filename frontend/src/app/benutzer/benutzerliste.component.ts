import {Component, inject, OnInit, signal} from '@angular/core';
import {BenutzerService} from './benutzer.service';
import {MatDialog} from '@angular/material/dialog';
import {BenutzerRequest, BenutzerResponse} from './benutzer.model';
import {BenutzerAnlegenDialogComponent} from './benutzer-anlegen-dialog.component';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatButton} from '@angular/material/button';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from '@angular/material/table';

@Component({
  selector: 'app-benutzerliste',
  templateUrl: './benutzerliste.component.html',
  imports: [
    MatProgressSpinner,
    MatButton,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatRow,
    MatHeaderRow,
    MatRowDef,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRowDef
  ]
})
export class BenutzerlisteComponent implements OnInit {
  private benutzerService = inject(BenutzerService);
  private dialog = inject(MatDialog);

  readonly benutzer = signal<BenutzerResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);
  readonly tabelleSpalten = ['name', 'email', 'rolle', 'bearbeiten'];

  ngOnInit():void {
    this.benutzerService.getAlleBenutzer().subscribe({
      next: (res) => {
        this.benutzer.set(res);
        this.loading.set(false);
      },
      error: () => {
        this.fehler.set('Die Benutzerliste kann nicht geladen werden.');
        this.loading.set(false);
      }
    });
  }

  benutzerAnlegenDialog(): void {
    const dialog = this.dialog.open(BenutzerAnlegenDialogComponent, {
      width: '400px',
      data: null
    });
    dialog.afterClosed().subscribe((req: BenutzerRequest | undefined) => {
      if (req) {
        this.benutzerService.benutzerAnlegen(req).subscribe({
          next: (neuerBenutzer) => {
            this.benutzer.update(liste => [...liste, neuerBenutzer])
          },
          error: () => {
            this.fehler.set('Der Benutzer konnte nicht angelegt werden.')
          }
        });
      }
    });
  }

  benutzerBearbeitenDialog(zuBearbeitenderBenutzer: BenutzerResponse): void {
    const dialog = this.dialog.open(BenutzerAnlegenDialogComponent, {
      width: '400px',
      data: zuBearbeitenderBenutzer
    });
    dialog.afterClosed().subscribe((req: BenutzerRequest | undefined) => {
      if (req) {
        this.benutzerService.benutzerAktualisieren(zuBearbeitenderBenutzer.id, req).subscribe({
          next: (aktualisierterBenutzer) => this.benutzer.update(
            liste => liste.map(eintrag => eintrag.id === aktualisierterBenutzer.id ? aktualisierterBenutzer : eintrag)
          ),
          error: () => {
            this.fehler.set('Der Benutzer konnte nicht aktualisiert werden.')
          }
        });
      }
    });
  }
}
