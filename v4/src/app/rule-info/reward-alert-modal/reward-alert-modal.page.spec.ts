import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RewardAlertModalPage } from './reward-alert-modal.page';

describe('RewardAlertModalPage', () => {
  let component: RewardAlertModalPage;
  let fixture: ComponentFixture<RewardAlertModalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RewardAlertModalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RewardAlertModalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
