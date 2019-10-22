import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvCommodityComponent } from './adv-commodity.component';

describe('AdvCommodityComponent', () => {
  let component: AdvCommodityComponent;
  let fixture: ComponentFixture<AdvCommodityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvCommodityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvCommodityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
