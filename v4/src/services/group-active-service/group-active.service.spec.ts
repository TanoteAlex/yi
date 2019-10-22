import { TestBed, inject } from '@angular/core/testing';

import { GroupActiveService } from './group-active.service';

describe('GroupActiveService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GroupActiveService]
    });
  });

  it('should be created', inject([GroupActiveService], (service: GroupActiveService) => {
    expect(service).toBeTruthy();
  }));
});
