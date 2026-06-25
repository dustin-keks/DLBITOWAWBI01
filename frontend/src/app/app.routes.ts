import { Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {ProjektlisteComponent} from './projekte/projektliste.component';

export const routes: Routes = [
  {path: 'projekte', component: ProjektlisteComponent},
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];
