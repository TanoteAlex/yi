import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {VersionNumberPage} from './version-number.page';

describe('VersionNumberPage', () => {
    let component: VersionNumberPage;
    let fixture: ComponentFixture<VersionNumberPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [VersionNumberPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(VersionNumberPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
