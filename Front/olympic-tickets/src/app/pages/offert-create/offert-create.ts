import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OfferteService } from '../../services/offerte-service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-offert-create',
  imports: [CommonModule,ReactiveFormsModule,RouterModule],
  templateUrl: './offert-create.html',
  styleUrl: './offert-create.css'
})

export class OffertCreate {
  createForm: FormGroup;
  errorMessage: string = "";
  constructor(
    private fb: FormBuilder,
    private offertSe: OfferteService,
    private routes: Router
  ) {
    this.createForm = fb.group({
      name: ["", Validators.required],
      price: ["", Validators.required, Validators.min(0)],
      availableSeats: ["", Validators.required, Validators.min(1)],
      max_People: ["", Validators.required, Validators.min(1)],
      description: ["", Validators.required],
      location: ["", Validators.required],
      date: ["", Validators.required],
      imageUrl: [""],
    });
  }


  onSubmit(): void {
    if (this.createForm.invalid) {
      this.errorMessage = "il faut remplir tous les champs";
    }
    this.offertSe.createOffert(this.createForm.value).subscribe({
      next: () => {
        this.routes.navigate(["/offert"]);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'erreur dans la creation de offert';


      }
    })
  }



}
