import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesAreaPage } from './sales-area.page';

describe('SalesAreaPage', () => {
  let component: SalesAreaPage;
  let fixture: ComponentFixture<SalesAreaPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalesAreaPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesAreaPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
