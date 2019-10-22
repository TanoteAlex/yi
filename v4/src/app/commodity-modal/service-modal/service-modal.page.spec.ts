import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ServiceModalPage} from './service-modal.page';

describe('ServiceModalPage', () => {
    let component: ServiceModalPage;
    let fixture: ComponentFixture<ServiceModalPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ServiceModalPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ServiceModalPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
