import {TestBed} from '@angular/core/testing';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';
import {roleGuard} from './role.guard';

describe('roleGuard', () => {
  const hasRolle = vi.fn();
  const createUrlTree = vi.fn();

  beforeEach(() => {
    hasRolle.mockReset();
    createUrlTree.mockReset();

    TestBed.resetTestingModule();

    TestBed.configureTestingModule({
      providers: [
        {provide: AuthService, useValue: {hasRolle}},
        {provide: Router, useValue: {createUrlTree}}
      ]
    });
  });

  it('erlaubt den Zugriff, wenn der Benutzer eine der geforderten Rollen hat', () => {
    hasRolle.mockReturnValue(true);
    const guard = roleGuard('ADMIN');

    const result = TestBed.runInInjectionContext(() => guard({} as any, {} as any));

    expect(hasRolle).toHaveBeenCalledWith('ADMIN');
    expect(result).toBe(true);
  });

  it('leitet zu /projekte um, wenn der Benutzer keine passende Rolle hat', () => {
    hasRolle.mockReturnValue(false);
    const urlTree = {} as any;
    createUrlTree.mockReturnValue(urlTree);
    const guard = roleGuard('ADMIN');

    const result = TestBed.runInInjectionContext(() => guard({} as any, {} as any));

    expect(createUrlTree).toHaveBeenCalledWith(['/projekte']);
    expect(result).toBe(urlTree);
  });
});
