import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitesReceivePrizePage } from './invites-receive-prize.page';

describe('InvitesReceivePrizePage', () => {
  let component: InvitesReceivePrizePage;
  let fixture: ComponentFixture<InvitesReceivePrizePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitesReceivePrizePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitesReceivePrizePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
