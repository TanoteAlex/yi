import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareClickModalPage } from './share-click-modal.page';

describe('ShareClickModalPage', () => {
  let component: ShareClickModalPage;
  let fixture: ComponentFixture<ShareClickModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShareClickModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareClickModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
