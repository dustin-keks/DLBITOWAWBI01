import {Component, inject, signal} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {BenutzerRequest, BenutzerResponse, BenutzerRolle} from './benutzer.model';
import {form, FormField, required} from '@angular/forms/signals';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatButton} from '@angular/material/button';

interface BenutzerAnlegenModel {
  name: string;
  email: string;
  passwort: string;
}

@Component({
  selector: 'app-benutzer-anlegen-dialog',
  templateUrl: './benutzer-anlegen-dialog.component.html',
  imports: [
    MatDialogContent,
    MatFormField,
    MatLabel,
    FormField,
    MatError,
    MatSelect,
    MatOption,
    MatDialogActions,
    MatInput,
    MatButton,
    MatDialogTitle
  ]
})
export class BenutzerAnlegenDialogComponent {
  private dialog = inject(MatDialogRef<BenutzerAnlegenDialogComponent>);
  readonly benutzer = inject<BenutzerResponse | null>(MAT_DIALOG_DATA);

  readonly rollenOptionen: BenutzerRolle[] = ['ADMIN', 'PROJEKTLEITER', 'MITARBEITER', 'BENUTZER'];
  readonly rolle = signal<BenutzerRolle>(this.benutzer?.rolle ?? 'MITARBEITER');

  readonly model = signal<BenutzerAnlegenModel>({
    name: this.benutzer?.name ?? '',
    email: this.benutzer?.email ?? '',
    passwort: ''
  });

  readonly benutzerForm = form(this.model, (schemaPath) => {
    required(schemaPath.name, {message: 'Der Name ist erforderlich'});
    required(schemaPath.email, {message: 'Die E-Mail-Adresse ist erforderlich'});
    if (this.benutzer === null) {
      required(schemaPath.passwort, {message: 'Das Passwort ist erforderlich'});
    }
  });

  abbrechen(): void {
    this.dialog.close();
  }

  speichern(event: Event): void {
    event.preventDefault();
    if (this.benutzerForm.name().valid() && this.benutzerForm.email().valid() && this.benutzerForm.passwort().valid()) {
      const req: BenutzerRequest = {
        name: this.model().name,
        email: this.model().email,
        passwort: this.model().passwort,
        rolle: this.rolle()
      }
      this.dialog.close(req);
    }
  }
}
