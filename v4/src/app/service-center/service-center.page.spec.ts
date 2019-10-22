import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ServiceCenterPage} from './service-center.page';

describe('ServiceCenterPage', () => {
    let component: ServiceCenterPage;
    let fixture: ComponentFixture<ServiceCenterPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ServiceCenterPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ServiceCenterPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
