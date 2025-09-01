import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Footer } from './footer';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('Footer', () => {
  let component: Footer;
  let fixture: ComponentFixture<Footer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Footer],
      providers: [
        { provide: ActivatedRoute, useValue: { params: of({}) } }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(Footer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
