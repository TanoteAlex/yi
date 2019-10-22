import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityGroupPage } from './commodity-group.page';

describe('CommodityGroupPage', () => {
  let component: CommodityGroupPage;
  let fixture: ComponentFixture<CommodityGroupPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityGroupPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityGroupPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
