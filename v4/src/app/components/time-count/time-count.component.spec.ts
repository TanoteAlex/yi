import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCountComponent } from './time-count.component';

describe('TimeCountComponent', () => {
  let component: TimeCountComponent;
  let fixture: ComponentFixture<TimeCountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
