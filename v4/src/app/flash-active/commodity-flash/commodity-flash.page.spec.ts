import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityFlashPage } from './commodity-flash.page';

describe('CommodityFlashPage', () => {
  let component: CommodityFlashPage;
  let fixture: ComponentFixture<CommodityFlashPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityFlashPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityFlashPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
