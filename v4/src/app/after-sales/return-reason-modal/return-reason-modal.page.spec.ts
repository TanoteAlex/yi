import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReturnReasonModalPage} from './return-reason-modal.page';

describe('ReturnReasonModalPage', () => {
    let component: ReturnReasonModalPage;
    let fixture: ComponentFixture<ReturnReasonModalPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ReturnReasonModalPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ReturnReasonModalPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
