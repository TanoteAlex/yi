import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CouponStorageComponent } from './coupon-storage.component';

describe('CouponStorageComponent', () => {
  let component: CouponStorageComponent;
  let fixture: ComponentFixture<CouponStorageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouponStorageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CouponStorageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
