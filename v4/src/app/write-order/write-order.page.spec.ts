import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {WriteOrderPage} from './write-order.page';

describe('WriteOrderPage', () => {
    let component: WriteOrderPage;
    let fixture: ComponentFixture<WriteOrderPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [WriteOrderPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(WriteOrderPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
