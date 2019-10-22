import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponSendPage } from './coupon-send.page';

describe('CouponSendPage', () => {
  let component: CouponSendPage;
  let fixture: ComponentFixture<CouponSendPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouponSendPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CouponSendPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
