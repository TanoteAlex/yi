import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyCodePage} from './my-code.page';

describe('MyCodePage', () => {
    let component: MyCodePage;
    let fixture: ComponentFixture<MyCodePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MyCodePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MyCodePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
