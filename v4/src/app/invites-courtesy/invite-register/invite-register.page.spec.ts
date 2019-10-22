import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InviteRegisterPage } from './invite-register.page';

describe('InviteRegisterPage', () => {
  let component: InviteRegisterPage;
  let fixture: ComponentFixture<InviteRegisterPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InviteRegisterPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InviteRegisterPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
