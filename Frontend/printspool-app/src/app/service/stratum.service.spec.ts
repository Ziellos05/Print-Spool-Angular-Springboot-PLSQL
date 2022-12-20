import { TestBed } from '@angular/core/testing';

import { StratumService } from './stratum.service';

describe('StratumService', () => {
  let service: StratumService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StratumService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
