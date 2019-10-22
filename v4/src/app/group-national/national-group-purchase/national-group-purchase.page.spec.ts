import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NationalGroupPurchasePage } from './national-group-purchase.page';

describe('NationalGroupPurchasePage', () => {
  let component: NationalGroupPurchasePage;
  let fixture: ComponentFixture<NationalGroupPurchasePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NationalGroupPurchasePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NationalGroupPurchasePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
