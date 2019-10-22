import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommoditiesListPage } from './commodities-list.page';

describe('CommoditiesListPage', () => {
  let component: CommoditiesListPage;
  let fixture: ComponentFixture<CommoditiesListPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommoditiesListPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommoditiesListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
