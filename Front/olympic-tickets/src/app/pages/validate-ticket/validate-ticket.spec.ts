import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateTicket } from './validate-ticket';

describe('ValidateTicket', () => {
  let component: ValidateTicket;
  let fixture: ComponentFixture<ValidateTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValidateTicket]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidateTicket);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
