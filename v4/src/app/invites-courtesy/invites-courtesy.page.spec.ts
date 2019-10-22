import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InvitesCourtesyPage} from './invites-courtesy.page';

describe('InvitesCourtesyPage', () => {
    let component: InvitesCourtesyPage;
    let fixture: ComponentFixture<InvitesCourtesyPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [InvitesCourtesyPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InvitesCourtesyPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
