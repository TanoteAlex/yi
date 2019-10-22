import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ConsigneeModalPage} from './consignee-modal.page';

describe('ConsigneeModalPage', () => {
    let component: ConsigneeModalPage;
    let fixture: ComponentFixture<ConsigneeModalPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ConsigneeModalPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ConsigneeModalPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
