import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FineBalancePage} from './fine-balance.page';

describe('FineBalancePage', () => {
    let component: FineBalancePage;
    let fixture: ComponentFixture<FineBalancePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [FineBalancePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(FineBalancePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
