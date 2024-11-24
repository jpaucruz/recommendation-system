import { TestBed } from '@angular/core/testing';

import { WebactionsService } from './webactions.service';

describe('WebactionsService', () => {
  let service: WebactionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WebactionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
