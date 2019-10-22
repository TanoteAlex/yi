import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommissionOrderPage } from './commission-order.page';

describe('CommissionOrderPage', () => {
  let component: CommissionOrderPage;
  let fixture: ComponentFixture<CommissionOrderPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommissionOrderPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionOrderPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
