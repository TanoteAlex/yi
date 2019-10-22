import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitesSharePagePage } from './invites-share-page.page';

describe('InvitesSharePagePage', () => {
  let component: InvitesSharePagePage;
  let fixture: ComponentFixture<InvitesSharePagePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitesSharePagePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitesSharePagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
