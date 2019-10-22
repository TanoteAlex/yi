import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {VersionUpPage} from './version-up.page';

describe('VersionUpPage', () => {
    let component: VersionUpPage;
    let fixture: ComponentFixture<VersionUpPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [VersionUpPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(VersionUpPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
