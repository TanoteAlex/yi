import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowTwoCommoditiesComponent } from './show-two-commodities.component';

describe('ShowTwoCommoditiesComponent', () => {
  let component: ShowTwoCommoditiesComponent;
  let fixture: ComponentFixture<ShowTwoCommoditiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowTwoCommoditiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowTwoCommoditiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
