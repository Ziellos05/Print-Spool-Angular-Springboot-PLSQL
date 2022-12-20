import { TestBed } from '@angular/core/testing';

import { PrintSpoolService } from './print-spool.service';

describe('PrintSpoolService', () => {
  let service: PrintSpoolService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintSpoolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
