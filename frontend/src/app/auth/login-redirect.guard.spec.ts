import {TestBed} from '@angular/core/testing';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';
import {loginRedirectGuard} from './login-redirect.guard';

describe('loginRedirectGuard', () => {
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

  it('erlaubt den Zugriff auf /login, wenn der Benutzer nicht angemeldet ist', () => {
    isAngemeldet.mockReturnValue(false);

    const result = TestBed.runInInjectionContext(() => loginRedirectGuard({} as any, {} as any));

    expect(result).toBe(true);
    expect(createUrlTree).not.toHaveBeenCalled();
  });

  it('leitet angemeldete Benutzer zu /projekte um', () => {
    isAngemeldet.mockReturnValue(true);
    const urlTree = {} as any;
    createUrlTree.mockReturnValue(urlTree);

    const result = TestBed.runInInjectionContext(() => loginRedirectGuard({} as any, {} as any));

    expect(createUrlTree).toHaveBeenCalledWith(['/projekte']);
    expect(result).toBe(urlTree);
  });
});
