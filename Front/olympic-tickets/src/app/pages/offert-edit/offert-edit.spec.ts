import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffertEdit } from './offert-edit';

describe('OffertEdit', () => {
  let component: OffertEdit;
  let fixture: ComponentFixture<OffertEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffertEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffertEdit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
