import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupOrderPage } from './group-order.page';

describe('GroupOrderPage', () => {
  let component: GroupOrderPage;
  let fixture: ComponentFixture<GroupOrderPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupOrderPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupOrderPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
