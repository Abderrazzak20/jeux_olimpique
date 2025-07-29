import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertDetail } from './offert-detail';

describe('OffertDetail', () => {
  let component: OffertDetail;
  let fixture: ComponentFixture<OffertDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffertDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffertDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
