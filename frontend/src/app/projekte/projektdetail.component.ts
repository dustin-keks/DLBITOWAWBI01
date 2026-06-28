import {Component, inject, input, OnInit, signal} from '@angular/core';
import {AufgabeService} from '../aufgaben/aufgabe.service';
import {MatDialog} from '@angular/material/dialog';
import {AufgabeResponse, AufgabeStatus} from '../aufgaben/aufgabe.model';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatList, MatListItem, MatListItemLine, MatListItemTitle} from '@angular/material/list';
import {MatOption, MatSelect} from '@angular/material/select';
import {AufgabeAnlegenDialogComponent} from '../aufgaben/aufgabe-anlegen-dialog.component';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';

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
  private dialog = inject(MatDialog);

  readonly projektId = input.required<string>();
  readonly projektName = signal(
    (history.state as {projektName?: string}).projektName ?? 'Projektdetails'
  );
  readonly aufgaben = signal<AufgabeResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);
  readonly statusOptionen: AufgabeStatus[] = ['OFFEN', 'IN_BEARBEITUNG', 'ERLEDIGT'];

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
}
