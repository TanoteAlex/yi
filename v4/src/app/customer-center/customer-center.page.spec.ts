import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CustomerCenterPage} from './customer-center.page';

describe('CustomerCenterPage', () => {
    let component: CustomerCenterPage;
    let fixture: ComponentFixture<CustomerCenterPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CustomerCenterPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CustomerCenterPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
