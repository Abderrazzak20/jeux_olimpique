import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertCreate } from './offert-create';

describe('OffertCreate', () => {
  let component: OffertCreate;
  let fixture: ComponentFixture<OffertCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffertCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffertCreate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
