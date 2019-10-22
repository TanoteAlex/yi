import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InviteAttentionPage } from './invite-attention.page';

describe('InviteAttentionPage', () => {
  let component: InviteAttentionPage;
  let fixture: ComponentFixture<InviteAttentionPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InviteAttentionPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InviteAttentionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
