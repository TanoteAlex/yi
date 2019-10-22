import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InviteRulePage} from './invite-rule.page';

describe('InviteRulePage', () => {
    let component: InviteRulePage;
    let fixture: ComponentFixture<InviteRulePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [InviteRulePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InviteRulePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
