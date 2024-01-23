import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PofileMoviesComponent } from './pofile-movies.component';

describe('PofileMoviesComponent', () => {
  let component: PofileMoviesComponent;
  let fixture: ComponentFixture<PofileMoviesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PofileMoviesComponent]
    });
    fixture = TestBed.createComponent(PofileMoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
