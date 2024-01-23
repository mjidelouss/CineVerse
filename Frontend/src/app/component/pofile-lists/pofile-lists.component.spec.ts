import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PofileListsComponent } from './pofile-lists.component';

describe('PofileListsComponent', () => {
  let component: PofileListsComponent;
  let fixture: ComponentFixture<PofileListsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PofileListsComponent]
    });
    fixture = TestBed.createComponent(PofileListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
