import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiveGiftPage } from './receive-gift.page';

describe('ReceiveGiftPage', () => {
  let component: ReceiveGiftPage;
  let fixture: ComponentFixture<ReceiveGiftPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceiveGiftPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiveGiftPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
