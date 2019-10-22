import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SystemSetPage} from './system-set.page';

describe('SystemSetPage', () => {
    let component: SystemSetPage;
    let fixture: ComponentFixture<SystemSetPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SystemSetPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SystemSetPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
