import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupShareModalPage } from './group-share-modal.page';

describe('GroupShareModalPage', () => {
  let component: GroupShareModalPage;
  let fixture: ComponentFixture<GroupShareModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupShareModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupShareModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
