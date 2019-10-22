import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ArticleCommentPage} from './article-comment.page';

describe('ArticleCommentPage', () => {
    let component: ArticleCommentPage;
    let fixture: ComponentFixture<ArticleCommentPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ArticleCommentPage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ArticleCommentPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
