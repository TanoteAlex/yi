import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriteOrderGroupPage } from './write-order-group.page';

describe('WriteOrderGroupPage', () => {
  let component: WriteOrderGroupPage;
  let fixture: ComponentFixture<WriteOrderGroupPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WriteOrderGroupPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriteOrderGroupPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
