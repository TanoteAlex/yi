import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GobackBtnComponent} from './goback-btn.component';

describe('GobackBtnComponent', () => {
    let component: GobackBtnComponent;
    let fixture: ComponentFixture<GobackBtnComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [GobackBtnComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(GobackBtnComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
