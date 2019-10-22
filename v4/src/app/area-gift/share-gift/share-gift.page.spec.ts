import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareGiftPage } from './share-gift.page';

describe('ShareGiftPage', () => {
  let component: ShareGiftPage;
  let fixture: ComponentFixture<ShareGiftPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShareGiftPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareGiftPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
