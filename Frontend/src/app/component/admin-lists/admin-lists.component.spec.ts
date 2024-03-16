import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminListsComponent } from './admin-lists.component';

describe('AdminListsComponent', () => {
  let component: AdminListsComponent;
  let fixture: ComponentFixture<AdminListsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminListsComponent]
    });
    fixture = TestBed.createComponent(AdminListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
