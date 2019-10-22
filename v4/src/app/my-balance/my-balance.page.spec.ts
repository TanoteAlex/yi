import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyBalancePage} from './my-balance.page';

describe('MyBalancePage', () => {
    let component: MyBalancePage;
    let fixture: ComponentFixture<MyBalancePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MyBalancePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MyBalancePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
