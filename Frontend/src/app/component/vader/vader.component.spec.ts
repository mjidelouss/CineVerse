import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VaderComponent } from './vader.component';

describe('VaderComponent', () => {
  let component: VaderComponent;
  let fixture: ComponentFixture<VaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VaderComponent]
    });
    fixture = TestBed.createComponent(VaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
