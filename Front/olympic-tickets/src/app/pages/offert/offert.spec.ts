import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Offert } from './offert';

describe('Offert', () => {
  let component: Offert;
  let fixture: ComponentFixture<Offert>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Offert]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Offert);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
