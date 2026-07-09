import {Component, computed, inject, input, OnInit, signal} from '@angular/core';
import {AufgabeService} from '../aufgaben/aufgabe.service';
import {MatDialog} from '@angular/material/dialog';
import {AufgabeRequest, AufgabeResponse, AufgabeStatus} from '../aufgaben/aufgabe.model';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatOption, MatSelect} from '@angular/material/select';
import {AufgabeAnlegenDialogComponent} from '../aufgaben/aufgabe-anlegen-dialog.component';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MitarbeiterZuordnenDialogComponent} from './mitarbeiter-zuordnen-dialog.component';
import {BenutzerResponse} from '../benutzer/benutzer.model';
import {ProjektService} from './projekt.service';
import {AuthService} from '../auth/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ProjektResponse} from './projekt.model';
import {ProjektAnlegenDialogComponent} from './projekt-anlegen-dialog.component';
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
import {MatChip, MatChipRemove, MatChipSet} from '@angular/material/chips';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-projektdetail',
  templateUrl: './projektdetail.component.html',
  imports: [
    MatProgressSpinner,
    MatSelect,
    MatOption,
    MatButton,
    RouterLink,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRowDef,
    MatChipSet,
    MatChip,
    MatIcon,
    MatChipRemove
  ]
})
export class ProjektdetailComponent implements OnInit {
  private aufgabeService = inject(AufgabeService);
  private projektService = inject(ProjektService);
  private authService = inject(AuthService);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  readonly projektId = input.required<string>();
  readonly aufgaben = signal<AufgabeResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);
  readonly statusOptionen: AufgabeStatus[] = ['OFFEN', 'IN_BEARBEITUNG', 'ERLEDIGT'];
  readonly kannMitarbeiterZuordnen = computed(() => {
    return this.authService.hasRolle('ADMIN', 'PROJEKTLEITER')
  });
  readonly projekt = signal<ProjektResponse | null>(null);
  readonly kannProjektBearbeiten = computed(() => {
    return this.authService.hasRolle('ADMIN', 'PROJEKTLEITER')
  });
  readonly aufgabenSpalten = ['titel', 'beschreibung', 'status', 'zugewiesenerBenutzer', 'bearbeiten', 'loeschen'];

  ngOnInit(): void {
    this.aufgabeService.getAufgaben(this.projektId()).subscribe({
      next: (aufgabe) => {
        this.aufgaben.set(aufgabe);
        this.loading.set(false);
      },
      error: () => {
        this.fehler.set('Die Aufgaben konnten nicht geladen werden.');
        this.loading.set(false);
      }
    });

    this.projektService.getProjekt(this.projektId()).subscribe({
      next: (projekt) => this.projekt.set(projekt),
      error: () => this.fehler.set('Das Projekt konnte nicht geladen werden.')
    });
  }

  aufgabeAnlegenDialog(): void {
    const dialog = this.dialog.open(AufgabeAnlegenDialogComponent, {
      width: '400px',
      data: null
    });

    dialog.afterClosed().subscribe((request: AufgabeRequest | undefined) => {
      if (request) {
        this.aufgabeService.aufgabeAnlegen(this.projektId(), request).subscribe({
          next: (neueAufgabe) => {
            this.aufgaben.update((liste) => [...liste, neueAufgabe]);
          },
          error: () => {
            this.fehler.set('Die Aufgabe konnte nicht angelegt werden.');
          }
        });
      }
    });
  }

  aufgabeBearbeitenDialog(aufgabe: AufgabeResponse): void {
    const dialog = this.dialog.open(AufgabeAnlegenDialogComponent, {
      width: '400px',
      data: aufgabe
    });

    dialog.afterClosed().subscribe((request: AufgabeRequest | undefined) => {
      if (request) {
        this.aufgabeService.aufgabeAktualisieren(this.projektId(), aufgabe.id, request).subscribe({
          next: (aktualisierteAufgabe) => {
            this.aufgaben.update((liste) =>
              liste.map((eintrag) => (eintrag.id === aktualisierteAufgabe.id ? aktualisierteAufgabe : eintrag))
            );
          }, error: () => {
            this.fehler.set('Die Aufgabe konnte nicht aktualisiert werden.');
          }
        });
      }
    });
  }

  statusAendern(aufgabe: AufgabeResponse, status: AufgabeStatus):void {
    this.aufgabeService.statusAendern(this.projektId(), aufgabe.id, status).subscribe({
      next: (neueAufgabe) => {
        this.aufgaben.update((liste) =>
          liste.map((aufgabe) => (aufgabe.id === neueAufgabe.id ? neueAufgabe : aufgabe))
        );
      },
      error: () => {
        this.fehler.set('Der Status konnte nicht geändert werden.');
      }
    })
  }

  mitarbeiterZuordnenDialog():void {
    const dialog = this.dialog.open(MitarbeiterZuordnenDialogComponent, {width: '400px'});
    dialog.afterClosed().subscribe((ausgewaehlt: BenutzerResponse | undefined) => {
      if (ausgewaehlt) {
        this.projektService.mitarbeiterZuordnen(this.projektId(), ausgewaehlt.id).subscribe({
          next: (res) => {
            this.projekt.set(res);
            this.snackBar.open('Der Mitarbeiter wurde dem Projekt zugeordnet.', 'OK', {duration: 3000});
          },
          error: () => {
            this.fehler.set('Der Mitarbeiter konnte keinem Projekt zugeordnet werden.');
          }
        })
      }
    });
  }

  projektBearbeitenDialog(): void {
    const dialog = this.dialog.open(ProjektAnlegenDialogComponent, {
      width: '400px',
      data: this.projekt()
    });

    dialog.afterClosed().subscribe((name: string | undefined) => {
      if (name) {
        this.projektService.projektAktualisieren(this.projektId(), {name}).subscribe({
          next: (res) => this.projekt.set(res),
          error: () => this.fehler.set('Das Projekt konnte nicht aktualisiert werden.')
        });
      }
    });
  }

  aufgabeLoeschen(aufgabe: AufgabeResponse): void {
    if (!confirm(`Soll die Aufgabe "${aufgabe.titel}" wirklich gelöscht werden?`)) {
      return;
    }

    this.aufgabeService.aufgabeLoeschen(this.projektId(), aufgabe.id).subscribe({
      next: () => {
        this.aufgaben.update((liste) => liste.filter((eintrag) => eintrag.id !== aufgabe.id));
      },
      error: () => {
        this.fehler.set('Die Aufgabe konnte nicht gelöscht werden.');
      }
    });
  }

  aufgabeZuweisen(aufgabe: AufgabeResponse, benutzerId: string | null): void {
    this.aufgabeService.aufgabeZuweisen(this.projektId(), aufgabe.id, benutzerId).subscribe({
      next: (aktualisierteAufgabe) => {
        this.aufgaben.update((liste) =>
          liste.map((eintrag) => (eintrag.id === aktualisierteAufgabe.id ? aktualisierteAufgabe : eintrag))
        );
      }, error: () => {
        this.fehler.set('Der Benutzer konnte der Aufgabe nicht zugewiesen werden.')
      }
    });
  }

  mitarbeiterEntfernen(mitarbeiter: BenutzerResponse): void {
    this.projektService.mitarbeiterEntfernen(this.projektId(), mitarbeiter.id).subscribe({
      next: (res) => this.projekt.set(res),
      error: () => this.fehler.set('Der Mitarbeiter konnte nicht aus dem Projekt entfernt werden.')
    });
  }
}
