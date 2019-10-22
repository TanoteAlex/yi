import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCommoditiesComponent } from './show-commodities.component';

describe('ShowCommoditiesComponent', () => {
  let component: ShowCommoditiesComponent;
  let fixture: ComponentFixture<ShowCommoditiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowCommoditiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCommoditiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
