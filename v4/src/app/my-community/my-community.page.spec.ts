import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyCommunityPage} from './my-community.page';

describe('MyCommunityPage', () => {
    let component: MyCommunityPage;
    let fixture: ComponentFixture<MyCommunityPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MyCommunityPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MyCommunityPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
