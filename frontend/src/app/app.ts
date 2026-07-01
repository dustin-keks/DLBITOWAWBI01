import {ChangeDetectionStrategy, Component, computed, inject} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from './auth/auth.service';
import {MatToolbar} from '@angular/material/toolbar';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatToolbar, MatButton, RouterLink],
  templateUrl: './app.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './app.scss'
})
export class App {
  private authService = inject(AuthService);
  private router = inject(Router);

  readonly benutzername = this.authService.name;
  readonly isAngemeldet = computed(() => this.authService.rolle() !== null);
  readonly isAdmin = computed(() => this.authService.hasRolle('ADMIN'));

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
