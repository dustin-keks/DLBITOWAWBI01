import {Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {ProjektlisteComponent} from './projekte/projektliste.component';
import {authGuard} from './auth/auth.guard';
import {loginRedirectGuard} from './auth/login-redirect.guard';

export const routes: Routes = [
  {path: 'projekte', component: ProjektlisteComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent, canActivate: [loginRedirectGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];
