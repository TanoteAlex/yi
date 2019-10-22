import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitesCodePage } from './invites-code.page';

describe('InvitesCodePage', () => {
  let component: InvitesCodePage;
  let fixture: ComponentFixture<InvitesCodePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitesCodePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitesCodePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
