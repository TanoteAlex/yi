import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MemberGradePage} from './member-grade.page';

describe('MemberGradePage', () => {
    let component: MemberGradePage;
    let fixture: ComponentFixture<MemberGradePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MemberGradePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MemberGradePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
