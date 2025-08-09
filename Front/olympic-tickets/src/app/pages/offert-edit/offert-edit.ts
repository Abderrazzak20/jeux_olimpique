import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OfferteService } from '../../services/offerte-service';
import { iif } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-offert-edit',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './offert-edit.html',
  styleUrl: './offert-edit.css'
})
export class OffertEdit implements OnInit {
  editForm: FormGroup;
  errorMessage: string = "";
  offertId!: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private offertS: OfferteService,
    private routes: Router
  ) {
    this.editForm = this.fb.group({
      name: ["", Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      availableSeats: ['', [Validators.required, Validators.min(0)]],
      max_People: ['', [Validators.required, Validators.min(1)]],
      description: ['', Validators.required],
      location: ['', Validators.required],
      date: ['', Validators.required],
      imageUrl: ['']
    });
  }
  ngOnInit(): void {
    this.offertId = + this.route.snapshot.paramMap.get("id")!;
    this.offertS.getOffertById(this.offertId).subscribe({
      next: (data) => {
        this.editForm.patchValue(data);
      },
      error: () => {
        this.errorMessage = "impossible de changerl'offre";
      }
    });
  }

  onSubmit(): void {
    if (this.editForm.invalid) {
      this.errorMessage = "Veuillez remplir tous les champs correctement.";
      return;
    }

    this.offertS.offertUpdate(this.offertId, this.editForm.value).subscribe({
      next: () => this.routes.navigate(['/offert']),
      error: (err) => this.errorMessage = err.error?.message || "Erreur lors de la mise à jour."
    });
  }
}
