import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertSales } from './offert-sales';

describe('OffertSales', () => {
  let component: OffertSales;
  let fixture: ComponentFixture<OffertSales>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffertSales]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffertSales);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
