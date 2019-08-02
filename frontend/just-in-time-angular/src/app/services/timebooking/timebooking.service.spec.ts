import { TestBed } from '@angular/core/testing';

import { TimeBookingService } from './timebooking.service';

describe('TimeBookingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TimeBookingService = TestBed.get(TimeBookingService);
    expect(service).toBeTruthy();
  });
});
