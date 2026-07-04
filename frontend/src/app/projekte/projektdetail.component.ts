import {Component, computed, inject, input, OnInit, signal} from '@angular/core';
import {AufgabeService} from '../aufgaben/aufgabe.service';
import {MatDialog} from '@angular/material/dialog';
import {AufgabeResponse, AufgabeStatus} from '../aufgaben/aufgabe.model';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatList, MatListItem, MatListItemLine, MatListItemTitle} from '@angular/material/list';
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

@Component({
  selector: 'app-projektdetail',
  templateUrl: './projektdetail.component.html',
  imports: [
    MatProgressSpinner,
    MatList,
    MatListItem,
    MatListItemTitle,
    MatSelect,
    MatOption,
    MatButton,
    MatListItemLine,
    RouterLink
  ]
})
export class ProjektdetailComponent implements OnInit {
  private aufgabeService = inject(AufgabeService);
  private projektService = inject(ProjektService);
  private authService = inject(AuthService);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  readonly projektId = input.required<string>();
  readonly projektName = signal(
    (history.state as {projektName?: string}).projektName ?? 'Projektdetails'
  );
  readonly aufgaben = signal<AufgabeResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);
  readonly statusOptionen: AufgabeStatus[] = ['OFFEN', 'IN_BEARBEITUNG', 'ERLEDIGT'];
  readonly kannMitarbeiterZuordnen = computed(() => {
    return this.authService.hasRolle('ADMIN', 'PROJEKTLEITER')
  });
  readonly projekt = signal<ProjektResponse | null>(null);

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
    const dialog = this.dialog.open(AufgabeAnlegenDialogComponent, {width: '400px'});

    dialog.afterClosed().subscribe((titel: string | undefined) => {
      if (titel) {
        this.aufgabeService.aufgabeAnlegen(this.projektId(), {titel}).subscribe({
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
}
