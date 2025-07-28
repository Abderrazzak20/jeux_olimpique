import { TestBed } from '@angular/core/testing';

import { OfferteService } from './offerte-service';

describe('OfferteService', () => {
  let service: OfferteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
