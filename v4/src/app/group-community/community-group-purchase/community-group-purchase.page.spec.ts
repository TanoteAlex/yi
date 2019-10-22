import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityGroupPurchasePage } from './community-group-purchase.page';

describe('CommunityGroupPurchasePage', () => {
  let component: CommunityGroupPurchasePage;
  let fixture: ComponentFixture<CommunityGroupPurchasePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommunityGroupPurchasePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunityGroupPurchasePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
