import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CouponReceiveFinishPage} from './coupon-receive-finish.page';

describe('CouponReceiveFinishPage', () => {
    let component: CouponReceiveFinishPage;
    let fixture: ComponentFixture<CouponReceiveFinishPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CouponReceiveFinishPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CouponReceiveFinishPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
