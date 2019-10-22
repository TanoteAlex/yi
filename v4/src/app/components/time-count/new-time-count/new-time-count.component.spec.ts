import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewTimeCountComponent } from './new-time-count.component';

describe('NewTimeCountComponent', () => {
  let component: NewTimeCountComponent;
  let fixture: ComponentFixture<NewTimeCountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewTimeCountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewTimeCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
