import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlashPurchasePage } from './flash-purchase.page';

describe('FlashPurchasePage', () => {
  let component: FlashPurchasePage;
  let fixture: ComponentFixture<FlashPurchasePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlashPurchasePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlashPurchasePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
