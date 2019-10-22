import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CouponOrderPage} from './coupon-order.page';

describe('CouponOrderPage', () => {
    let component: CouponOrderPage;
    let fixture: ComponentFixture<CouponOrderPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CouponOrderPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CouponOrderPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
