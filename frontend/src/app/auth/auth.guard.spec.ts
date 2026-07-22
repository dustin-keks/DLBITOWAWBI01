import {Router} from '@angular/router';
import {TestBed} from '@angular/core/testing';
import {AuthService} from './auth.service';
import {expect} from 'vitest';
import {authGuard} from './auth.guard';

describe('authGuard', () => {
  const isAngemeldet = vi.fn();
  const createUrlTree = vi.fn();

  beforeEach(() => {
    isAngemeldet.mockReset();
    createUrlTree.mockReset();

    TestBed.resetTestingModule();

    TestBed.configureTestingModule({
      providers: [
        {provide: AuthService, useValue: {isAngemeldet}},
        {provide: Router, useValue: {createUrlTree}}
      ]
    });
  });

  it('erlaubt den Zugriff, wenn der Benutzer angemeldet ist', () => {
    isAngemeldet.mockReturnValue(true);

    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    expect(result).toBe(true);
    expect(createUrlTree).not.toHaveBeenCalled();
  });

  it('leitet zu /login um, wenn der Benutzer nicht angemeldet ist', () => {
    isAngemeldet.mockReturnValue(false);
    const urlTree = {} as any;
    createUrlTree.mockReturnValue(urlTree);

    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    expect(createUrlTree).toHaveBeenCalledWith(['/login']);
    expect(result).toBe(urlTree);
  })
});
