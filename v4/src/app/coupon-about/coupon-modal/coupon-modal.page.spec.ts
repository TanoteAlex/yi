import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponModalPage } from './coupon-modal.page';

describe('CouponModalPage', () => {
  let component: CouponModalPage;
  let fixture: ComponentFixture<CouponModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouponModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CouponModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
