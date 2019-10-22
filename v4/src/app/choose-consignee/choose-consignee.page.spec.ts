import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChooseConsigneePage} from './choose-consignee.page';

describe('ChooseConsigneePage', () => {
    let component: ChooseConsigneePage;
    let fixture: ComponentFixture<ChooseConsigneePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ChooseConsigneePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ChooseConsigneePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
