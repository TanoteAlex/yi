import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommodityPage} from "./commodity.page";

describe('CommodityPage', () => {
    let component: CommodityPage;
    let fixture: ComponentFixture<CommodityPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CommodityPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CommodityPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
