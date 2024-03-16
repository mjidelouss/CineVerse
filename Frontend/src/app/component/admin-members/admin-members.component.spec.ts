import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMembersComponent } from './admin-members.component';

describe('AdminMembersComponent', () => {
  let component: AdminMembersComponent;
  let fixture: ComponentFixture<AdminMembersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminMembersComponent]
    });
    fixture = TestBed.createComponent(AdminMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
