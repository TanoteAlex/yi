import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowThreeCommoditiesComponent } from './show-three-commodities.component';

describe('ShowThreeCommoditiesComponent', () => {
  let component: ShowThreeCommoditiesComponent;
  let fixture: ComponentFixture<ShowThreeCommoditiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowThreeCommoditiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowThreeCommoditiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
