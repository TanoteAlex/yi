import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignSuccessModalPage } from './sign-success-modal.page';

describe('SignSuccessModalPage', () => {
  let component: SignSuccessModalPage;
  let fixture: ComponentFixture<SignSuccessModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignSuccessModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignSuccessModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
