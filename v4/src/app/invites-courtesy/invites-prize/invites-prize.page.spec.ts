import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvitesPrizePage } from './invites-prize.page';

describe('InvitesPrizePage', () => {
  let component: InvitesPrizePage;
  let fixture: ComponentFixture<InvitesPrizePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvitesPrizePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvitesPrizePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
