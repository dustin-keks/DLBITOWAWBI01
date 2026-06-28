import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {ProjektService} from './projekt.service';
import {ProjektResponse} from './projekt.model';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardFooter,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import {MatProgressBar} from '@angular/material/progress-bar';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {ProjektAnlegenDialogComponent} from './projekt-anlegen-dialog.component';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-projektliste',
  templateUrl: './projektliste.component.html',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardSubtitle,
    MatCardContent,
    MatProgressBar,
    MatProgressSpinner,
    MatButton,
    MatCardActions,
    MatCardFooter
  ]
})
export class ProjektlisteComponent implements OnInit {
  private authService = inject(AuthService);
  private projektService = inject(ProjektService);
  private dialog = inject(MatDialog);
  private router = inject(Router);

  readonly projekte = signal<ProjektResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);
  readonly kannProjektAnlegen = computed(() =>
    this.authService.hasRolle('ADMIN', 'PROJEKTLEITER')
  );

  ngOnInit(): void {
    this.projektService.getMeineProjekte().subscribe({
      next: (data) => {
        this.projekte.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.fehler.set('Die Projekte können nicht geladen werden.');
        this.loading.set(false);
      }
    })
  }

  projektAnlegenDialog(): void {
    const dialog = this.dialog.open(ProjektAnlegenDialogComponent, {
      width: '400px'
    });

    dialog.afterClosed().subscribe((name: string | undefined) => {
      if (name) {
        this.projektService.projektAnlegen({name}).subscribe({
          next: (res) => {
            this.projekte.update((liste) => [...liste, res]);
          },
          error: () => {
            this.fehler.set('Das Projekt konnte nicht angelegt werden.');
          }
        })
      }
    })
  }

  statusAendern(projekt: ProjektResponse, status: 'AKTIV' | 'ARCHIVIERT'): void {
    this.projektService.statusAendern(projekt.id, status).subscribe({
      next: (res) => {
        this.projekte.update((liste) =>
          liste.map((proj) => (proj.id === res.id ? res : proj))
        );
      },
      error: () => {
        this.fehler.set('Der Projektstatus konnte nicht geändert werden.');
      }
    })
  }

  projektAnzeigen(projekt: ProjektResponse):void {
    this.router.navigate(['/projekte', projekt.id], {state: {projektName: projekt.name}});
  }
}
