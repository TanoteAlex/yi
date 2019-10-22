import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowButtonsMode1Component } from './show-buttons-mode1.component';

describe('ShowButtonsMode1Component', () => {
  let component: ShowButtonsMode1Component;
  let fixture: ComponentFixture<ShowButtonsMode1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowButtonsMode1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowButtonsMode1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
