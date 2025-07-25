import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Offerts } from './offerts';

describe('Offerts', () => {
  let component: Offerts;
  let fixture: ComponentFixture<Offerts>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Offerts]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Offerts);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
