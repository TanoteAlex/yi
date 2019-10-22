import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowButtonsListComponent } from './show-buttons-list.component';

describe('ShowButtonsListComponent', () => {
  let component: ShowButtonsListComponent;
  let fixture: ComponentFixture<ShowButtonsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowButtonsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowButtonsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
