import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommListPage} from './comm-list.page';

describe('CommListPage', () => {
    let component: CommListPage;
    let fixture: ComponentFixture<CommListPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CommListPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CommListPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
