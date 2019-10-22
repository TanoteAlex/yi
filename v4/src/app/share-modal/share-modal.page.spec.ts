import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareModalPage } from './share-modal.page';

describe('ShareModalPage', () => {
  let component: ShareModalPage;
  let fixture: ComponentFixture<ShareModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShareModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
