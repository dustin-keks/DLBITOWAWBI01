import {Component, inject, OnInit, signal} from '@angular/core';
import {ProjektService} from './projekt.service';
import {ProjektResponse} from './projekt.model';
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from '@angular/material/card';
import {MatProgressBar} from '@angular/material/progress-bar';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

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
    MatProgressSpinner
  ]
})
export class ProjektlisteComponent implements OnInit {
  private projektService = inject(ProjektService);

  readonly projekte = signal<ProjektResponse[]>([]);
  readonly loading = signal(true);
  readonly fehler = signal<string | null>(null);

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
}
