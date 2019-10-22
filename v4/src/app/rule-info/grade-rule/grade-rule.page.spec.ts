import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GradeRulePage} from './grade-rule.page';

describe('GradeRulePage', () => {
    let component: GradeRulePage;
    let fixture: ComponentFixture<GradeRulePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [GradeRulePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(GradeRulePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
