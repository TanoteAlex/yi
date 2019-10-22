import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AuditProgressPage} from './audit-progress.page';

describe('AuditProgressPage', () => {
    let component: AuditProgressPage;
    let fixture: ComponentFixture<AuditProgressPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AuditProgressPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AuditProgressPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
