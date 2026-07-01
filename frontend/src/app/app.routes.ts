import {Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {ProjektlisteComponent} from './projekte/projektliste.component';
import {authGuard} from './auth/auth.guard';
import {loginRedirectGuard} from './auth/login-redirect.guard';
import {ProjektdetailComponent} from './projekte/projektdetail.component';
import {BenutzerlisteComponent} from './benutzer/benutzerliste.component';
import {roleGuard} from './auth/role.guard';

export const routes: Routes = [
  {path: 'admin/benutzer', component: BenutzerlisteComponent, canActivate: [authGuard, roleGuard('ADMIN')]},
  {path: 'projekte/:projektId', component: ProjektdetailComponent, canActivate: [authGuard]},
  {path: 'projekte', component: ProjektlisteComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent, canActivate: [loginRedirectGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];
