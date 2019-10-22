import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoveLifePage} from './love-life.page';

describe('LoveLifePage', () => {
    let component: LoveLifePage;
    let fixture: ComponentFixture<LoveLifePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [LoveLifePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(LoveLifePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
