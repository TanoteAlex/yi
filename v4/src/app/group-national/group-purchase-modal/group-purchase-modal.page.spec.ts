import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupPurchaseModalPage } from './group-purchase-modal.page';

describe('GroupPurchaseModalPage', () => {
  let component: GroupPurchaseModalPage;
  let fixture: ComponentFixture<GroupPurchaseModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupPurchaseModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupPurchaseModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
