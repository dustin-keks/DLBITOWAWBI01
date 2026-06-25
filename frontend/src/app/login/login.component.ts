import {Component, signal} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {form, FormField, required} from '@angular/forms/signals';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';

interface LoginModel {
  email: string;
  passwort: string;
}

@Component({
  selector: 'app-login',
  imports: [
    FormField,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent{
  readonly model = signal<LoginModel>({email: '', passwort: ''});
  readonly loginForm = form(this.model, (schemaPath) => {
    required(schemaPath.email, {message: 'Die E-Mail-Adresse ist erforderlich'});
    required(schemaPath.passwort, {message: 'Das Passwort ist erforderlich'});
  });
  readonly fehler = signal<string | null>(null);

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(event: Event) {
    event.preventDefault();
    this.fehler.set(null);
    this.authService.login(this.model()).subscribe({
      next: () => this.router.navigateByUrl('/projekte'),
      error: () => this.fehler.set('Deine E-Mail-Adresse oder Passwort sind falsch')
    });
  }
}
