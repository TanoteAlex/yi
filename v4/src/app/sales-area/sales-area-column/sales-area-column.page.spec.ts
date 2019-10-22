import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesAreaColumnPage } from './sales-area-column.page';

describe('SalesAreaColumnPage', () => {
  let component: SalesAreaColumnPage;
  let fixture: ComponentFixture<SalesAreaColumnPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalesAreaColumnPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesAreaColumnPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
