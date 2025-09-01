import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Home } from './home';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('Home', () => {
  let component: Home;
  let fixture: ComponentFixture<Home>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Home],
       providers: [
        
        { provide: ActivatedRoute, useValue: { params: of({}) } }

      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
