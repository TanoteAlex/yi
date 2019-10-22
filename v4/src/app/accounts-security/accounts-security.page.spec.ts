import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountsSecurityPage} from './accounts-security.page';

describe('AccountsSecurityPage', () => {
    let component: AccountsSecurityPage;
    let fixture: ComponentFixture<AccountsSecurityPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AccountsSecurityPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AccountsSecurityPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
