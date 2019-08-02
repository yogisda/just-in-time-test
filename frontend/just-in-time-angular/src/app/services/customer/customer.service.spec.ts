import { TestBed } from '@angular/core/testing';

import { CustomerService } from './customer.service';

describe('TimebookingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CustomerService = TestBed.get(CustomerService);
    expect(service).toBeTruthy();
  });
});
