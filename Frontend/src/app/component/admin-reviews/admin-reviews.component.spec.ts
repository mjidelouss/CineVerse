import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminReviewsComponent } from './admin-reviews.component';

describe('AdminReviewsComponent', () => {
  let component: AdminReviewsComponent;
  let fixture: ComponentFixture<AdminReviewsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminReviewsComponent]
    });
    fixture = TestBed.createComponent(AdminReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
