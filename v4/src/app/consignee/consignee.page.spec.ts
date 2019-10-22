import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ConsigneePage} from './consignee.page';

describe('ConsigneePage', () => {
    let component: ConsigneePage;
    let fixture: ComponentFixture<ConsigneePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ConsigneePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ConsigneePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
