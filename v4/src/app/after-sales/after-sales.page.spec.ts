import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AfterSalesPage} from './after-sales.page';

describe('AfterSalesPage', () => {
    let component: AfterSalesPage;
    let fixture: ComponentFixture<AfterSalesPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AfterSalesPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AfterSalesPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
