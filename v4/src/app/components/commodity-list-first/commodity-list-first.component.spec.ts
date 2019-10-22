import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityListFirstComponent } from './commodity-list-first.component';

describe('CommodityListFirstComponent', () => {
  let component: CommodityListFirstComponent;
  let fixture: ComponentFixture<CommodityListFirstComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityListFirstComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityListFirstComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
