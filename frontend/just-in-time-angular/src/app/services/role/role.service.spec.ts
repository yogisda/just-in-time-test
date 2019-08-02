import { TestBed } from '@angular/core/testing';

import { RoleService } from './role.service';

describe('TimebookingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RoleService = TestBed.get(RoleService);
    expect(service).toBeTruthy();
  });
});
