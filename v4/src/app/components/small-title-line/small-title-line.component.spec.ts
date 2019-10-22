import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallTitleLineComponent } from './small-title-line.component';

describe('SmallTitleLineComponent', () => {
  let component: SmallTitleLineComponent;
  let fixture: ComponentFixture<SmallTitleLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmallTitleLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmallTitleLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
