import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AutherService } from '../../services/auther-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [CommonModule,FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  email = "";
  password = "";
  errorMessage = "";

  constructor(private authS: AutherService, private router: Router) { }

  onLogin() {
    this.authS.login(this.email, this.password).subscribe({
      next: (res) => {
        this.authS.saveToken(res.token);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.errorMessage = 'Identifiants incorrects ou erreur serveur';
      }
    })
  }
}

