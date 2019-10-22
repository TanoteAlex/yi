import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApplyReturnPage} from './apply-return.page';

describe('ApplyReturnPage', () => {
    let component: ApplyReturnPage;
    let fixture: ComponentFixture<ApplyReturnPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ApplyReturnPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ApplyReturnPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
