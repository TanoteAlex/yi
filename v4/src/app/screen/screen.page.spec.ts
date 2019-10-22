import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ScreenPage} from './screen.page';

describe('ScreenPage', () => {
    let component: ScreenPage;
    let fixture: ComponentFixture<ScreenPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ScreenPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ScreenPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
