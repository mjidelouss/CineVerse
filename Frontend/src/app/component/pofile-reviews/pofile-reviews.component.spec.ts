import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PofileReviewsComponent } from './pofile-reviews.component';

describe('PofileReviewsComponent', () => {
  let component: PofileReviewsComponent;
  let fixture: ComponentFixture<PofileReviewsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PofileReviewsComponent]
    });
    fixture = TestBed.createComponent(PofileReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
