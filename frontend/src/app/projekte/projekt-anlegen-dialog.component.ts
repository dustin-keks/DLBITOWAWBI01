import {Component, inject, signal} from '@angular/core';
import {form, FormField, required} from '@angular/forms/signals';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {ProjektResponse} from './projekt.model';

interface ProjektAnlegenModel {
  name: string;
}

@Component({
  selector: 'app-projekt-anlegen-dialog',
  templateUrl: './projekt-anlegen-dialog.component.html',
  imports: [
    MatDialogContent,
    MatDialogTitle,
    MatFormField,
    MatLabel,
    MatInput,
    FormField,
    MatError,
    MatDialogActions,
    MatButton
  ]
})
export class ProjektAnlegenDialogComponent {
  private dialog = inject(MatDialogRef<ProjektAnlegenDialogComponent>);
  readonly projekt = inject<ProjektResponse | null>(MAT_DIALOG_DATA);

  readonly model = signal<ProjektAnlegenModel>({name: this.projekt?.name ?? ''});
  readonly projektForm = form(this.model, (schemaPath) => {
    required(schemaPath.name, {message: 'Der Projektname ist erforderlich'});
  });

  abbrechen():void {
    this.dialog.close();
  }

  speichern(event: Event): void {
    event.preventDefault();
    if (this.projektForm.name().valid()) {
      this.dialog.close(this.model().name);
    }
  }
}
