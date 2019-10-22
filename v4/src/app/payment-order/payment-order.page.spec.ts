import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PaymentOrderPage} from './payment-order.page';

describe('PaymentOrderPage', () => {
    let component: PaymentOrderPage;
    let fixture: ComponentFixture<PaymentOrderPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PaymentOrderPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PaymentOrderPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
