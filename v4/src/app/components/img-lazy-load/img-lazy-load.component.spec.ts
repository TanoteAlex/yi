import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImgLazyLoadComponent } from './img-lazy-load.component';

describe('ImgLazyLoadComponent', () => {
  let component: ImgLazyLoadComponent;
  let fixture: ComponentFixture<ImgLazyLoadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImgLazyLoadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImgLazyLoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
