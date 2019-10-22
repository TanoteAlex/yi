import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttrGroupModalPage } from './attr-group-modal.page';

describe('AttrGroupModalPage', () => {
  let component: AttrGroupModalPage;
  let fixture: ComponentFixture<AttrGroupModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttrGroupModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttrGroupModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
