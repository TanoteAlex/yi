import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangePwdPage} from './change-pwd.page';

describe('ChangePwdPage', () => {
    let component: ChangePwdPage;
    let fixture: ComponentFixture<ChangePwdPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ChangePwdPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ChangePwdPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
