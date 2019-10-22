import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FooterTipComponent} from './footer-tip.component';

describe('FooterTipComponent', () => {
    let component: FooterTipComponent;
    let fixture: ComponentFixture<FooterTipComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [FooterTipComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(FooterTipComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
