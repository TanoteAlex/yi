import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CouponReceivePage} from './coupon-receive.page';

describe('CouponReceivePage', () => {
    let component: CouponReceivePage;
    let fixture: ComponentFixture<CouponReceivePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CouponReceivePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CouponReceivePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
