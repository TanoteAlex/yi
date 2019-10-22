import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentFailPage } from './payment-fail.page';

describe('PaymentFailPage', () => {
  let component: PaymentFailPage;
  let fixture: ComponentFixture<PaymentFailPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentFailPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentFailPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
