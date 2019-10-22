import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictModalPage } from './district-modal.page';

describe('DistrictModalPage', () => {
  let component: DistrictModalPage;
  let fixture: ComponentFixture<DistrictModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistrictModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistrictModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
