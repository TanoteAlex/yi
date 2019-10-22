import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityPerformancePage } from './community-performance.page';

describe('CommunityPerformancePage', () => {
  let component: CommunityPerformancePage;
  let fixture: ComponentFixture<CommunityPerformancePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommunityPerformancePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunityPerformancePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
