import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from './auth.service';

export const roleGuard = (...rollen: string[]): CanActivateFn => () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.hasRolle(...rollen)) {
    return true;
  }

  return router.createUrlTree(['/projekte']);
}
