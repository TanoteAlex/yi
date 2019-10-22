import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaGiftPage } from './area-gift.page';

describe('AreaGiftPage', () => {
  let component: AreaGiftPage;
  let fixture: ComponentFixture<AreaGiftPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AreaGiftPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaGiftPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
