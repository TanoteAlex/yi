import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupSharePagePage } from './group-share-page.page';

describe('GroupSharePagePage', () => {
  let component: GroupSharePagePage;
  let fixture: ComponentFixture<GroupSharePagePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupSharePagePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupSharePagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
