import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaDoubleStarPage } from './area-double-star.page';

describe('AreaDoubleStarPage', () => {
  let component: AreaDoubleStarPage;
  let fixture: ComponentFixture<AreaDoubleStarPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AreaDoubleStarPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaDoubleStarPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
