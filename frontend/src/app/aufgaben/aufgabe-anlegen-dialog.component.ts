import {Component, inject, signal} from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {form, FormField, required} from '@angular/forms/signals';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

interface AufgabeAnlegenModel {
  titel: string;
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

  readonly model = signal<AufgabeAnlegenModel>({titel: ''});
  readonly aufgabeForm = form(this.model, (schemaPath) => {
    required(schemaPath.titel, {message: 'Der Titel ist erforderlich.'});
  });

  abbrechen(): void {
    this.dialog.close();
  }

  speichern(event: Event):void {
    event.preventDefault();
    if (this.aufgabeForm.titel().valid()) {
      this.dialog.close(this.model().titel);
    }
  }
}
