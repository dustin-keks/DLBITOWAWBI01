import {Component, inject, OnInit, signal} from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {BenutzerService} from '../benutzer/benutzer.service';
import {BenutzerResponse} from '../benutzer/benutzer.model';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-mitarbeiter-zuordnen-dialog',
  templateUrl: './mitarbeiter-zuordnen-dialog.component.html',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatProgressSpinner,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatDialogActions,
    MatButton
  ]
})
export class MitarbeiterZuordnenDialogComponent implements OnInit {
  private dialog = inject(MatDialogRef<MitarbeiterZuordnenDialogComponent>);
  private benutzerService = inject(BenutzerService);

  readonly benutzer = signal<BenutzerResponse[]>([]);
  readonly loading = signal(true);
  readonly ausgewaehlt = signal<BenutzerResponse | null>(null);
  readonly fehler = signal<string | null>(null);

  ngOnInit(): void {
    this.benutzerService.getAlleBenutzer().subscribe({
      next: (res) => {
        this.benutzer.set(res);
        this.loading.set(false);
      },
      error: () => {
        this.fehler.set('Die Benutzerliste konnte nicht geladen werden.');
        this.loading.set(false);
      }
    });
  }

  abbrechen(): void {
    this.dialog.close();
  }

  zuordnen(): void {
    const ausgewaehlt = this.ausgewaehlt();
    if (ausgewaehlt) {
      this.dialog.close(ausgewaehlt);
    }
  }
}
