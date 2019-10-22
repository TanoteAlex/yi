import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitedMemberPage } from './invited-member.page';

describe('InvitedMemberPage', () => {
  let component: InvitedMemberPage;
  let fixture: ComponentFixture<InvitedMemberPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitedMemberPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitedMemberPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
