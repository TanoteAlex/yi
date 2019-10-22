import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponClickModalPage } from './coupon-click-modal.page';

describe('CouponClickModalPage', () => {
  let component: CouponClickModalPage;
  let fixture: ComponentFixture<CouponClickModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouponClickModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CouponClickModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
