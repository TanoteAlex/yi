import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowFourCommoditiesComponent } from './show-four-commodities.component';

describe('ShowFourCommoditiesComponent', () => {
  let component: ShowFourCommoditiesComponent;
  let fixture: ComponentFixture<ShowFourCommoditiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowFourCommoditiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowFourCommoditiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
