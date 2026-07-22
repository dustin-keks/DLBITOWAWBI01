import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideHttpClient} from '@angular/common/http';
import {ProjektService} from './projekt.service';

describe('ProjektService', () => {
  let projektService: ProjektService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.resetTestingModule();

    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    projektService = TestBed.inject(ProjektService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('projektAnlegen() sendet den Namen per POST', () => {
    projektService.projektAnlegen({name: 'Ein toller Projektname'}).subscribe();
    const req = httpMock.expectOne('http://localhost:8080/api/projekte');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({name: 'Ein toller Projektname'});
  });

  it('getMeineProjekte() ruft die Projektliste per GET ab', () => {
    projektService.projektAnlegen({name: 'bla blub bla'}).subscribe();
    projektService.projektAnlegen({name: 'Hallo Welt'}).subscribe();
    const angelegteProjekte = httpMock.match(
      (request) => request.method === 'POST' && request.url === 'http://localhost:8080/api/projekte'
    );
    expect(angelegteProjekte.length).toBe(2);
    expect(angelegteProjekte[0].request.body).toEqual({name: 'bla blub bla'});
    expect(angelegteProjekte[1].request.body).toEqual({name: 'Hallo Welt'});

    projektService.getMeineProjekte().subscribe();
    const geladeneListe = httpMock.expectOne({method: 'GET', url: 'http://localhost:8080/api/projekte'});
    expect(geladeneListe.request.method).toBe('GET')
  });

  it('statusAendern() sendet den neuen Status per PATCH', () => {
    projektService.statusAendern('12345', 'ARCHIVIERT').subscribe();
    const req = httpMock.expectOne('http://localhost:8080/api/projekte/12345/status');
    expect(req.request.method).toBe('PATCH');
    expect(req.request.body).toEqual({status: 'ARCHIVIERT'});
  });
});
