import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ParamsModalPage} from './params-modal.page';

describe('ParamsModalPage', () => {
    let component: ParamsModalPage;
    let fixture: ComponentFixture<ParamsModalPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ParamsModalPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ParamsModalPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
