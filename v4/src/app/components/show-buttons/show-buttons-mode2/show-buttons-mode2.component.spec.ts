import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowButtonsMode2Component } from './show-buttons-mode2.component';

describe('ShowButtonsMode2Component', () => {
  let component: ShowButtonsMode2Component;
  let fixture: ComponentFixture<ShowButtonsMode2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowButtonsMode2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowButtonsMode2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
