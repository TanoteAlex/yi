import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupShareSuccessModalPage } from './group-share-success-modal.page';

describe('GroupShareSuccessModalPage', () => {
  let component: GroupShareSuccessModalPage;
  let fixture: ComponentFixture<GroupShareSuccessModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupShareSuccessModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupShareSuccessModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
