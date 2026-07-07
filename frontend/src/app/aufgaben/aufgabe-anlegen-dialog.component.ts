import {Component, inject, signal} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {form, FormField, required} from '@angular/forms/signals';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {AufgabeResponse} from './aufgabe.model';

interface AufgabeAnlegenModel {
  titel: string;
  beschreibung: string;
}

@Component({
  selector: 'app-aufgabe-anlegen-dialog',
  templateUrl: './aufgabe-anlegen-dialog.component.html',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatFormField,
    MatLabel,
    MatInput,
    FormField,
    MatError,
    MatDialogActions,
    MatButton
  ]
})
export class AufgabeAnlegenDialogComponent {
  private dialog = inject(MatDialogRef<AufgabeAnlegenDialogComponent>);
  readonly aufgabe = inject<AufgabeResponse | null>(MAT_DIALOG_DATA);

  readonly model = signal<AufgabeAnlegenModel>({
    titel: this.aufgabe?.titel ?? '',
    beschreibung: this.aufgabe?.beschreibung ?? ''
  });
  readonly aufgabeForm = form(this.model, (schemaPath) => {
    required(schemaPath.titel, {message: 'Der Titel ist erforderlich.'});
  });

  abbrechen(): void {
    this.dialog.close();
  }

  speichern(event: Event):void {
    event.preventDefault();
    if (this.aufgabeForm.titel().valid()) {
      this.dialog.close(this.model());
    }
  }
}
