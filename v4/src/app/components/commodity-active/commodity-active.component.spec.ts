import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityActiveComponent } from './commodity-active.component';

describe('CommodityActiveComponent', () => {
  let component: CommodityActiveComponent;
  let fixture: ComponentFixture<CommodityActiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityActiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityActiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
