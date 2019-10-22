import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AwaitPage} from './await.page';

describe('AwaitPage', () => {
    let component: AwaitPage;
    let fixture: ComponentFixture<AwaitPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AwaitPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AwaitPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
