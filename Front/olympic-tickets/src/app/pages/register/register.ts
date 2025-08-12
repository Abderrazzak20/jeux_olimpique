import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  registerForm: FormGroup;
  errorMessage: string = "";

  constructor(
    private fb: FormBuilder,
    private authS: AutherService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      admin:[false]
    })
  }


  onSubmit() {
    if (this.registerForm.invalid) return;
    const user = this.registerForm.value;
    this.authS.register(user).subscribe({
      next: () => {
        console.log("scemooooo",user);
        
        this.router.navigate(['/home'])
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Errore durante la registrazione';
      }
    })
  }
  
}
