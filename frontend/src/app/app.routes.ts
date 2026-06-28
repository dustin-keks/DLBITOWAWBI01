import {Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {ProjektlisteComponent} from './projekte/projektliste.component';
import {authGuard} from './auth/auth.guard';
import {loginRedirectGuard} from './auth/login-redirect.guard';
import {ProjektdetailComponent} from './projekte/projektdetail.component';

export const routes: Routes = [
  {path: 'projekte/:id', component: ProjektdetailComponent, canActivate: [authGuard]},
  {path: 'projekte', component: ProjektlisteComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent, canActivate: [loginRedirectGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];
