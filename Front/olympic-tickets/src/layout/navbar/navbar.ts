import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AutherService } from '../../app/services/auther-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
  constructor(private router: Router, private authS: AutherService) { }

  menuOpen: boolean = false;
  isLoggedIn(): boolean {
    return this.authS.isLoggin();
  }
  is_Admin(): boolean {
    return this.authS.is_Admin();
  }

  getUserEmail(): string | null {
    const token = this.authS.getToken();
    if (!token) return null;
    try {

      const payload = JSON.parse(atob(token.split(".")[1]));
      if (!payload.sub) return null;

      const email: string = payload.sub;
      const nome = email.split("@")[0];
      return nome;

    } catch (error) {
      return null;
    }
  }

  logout(): void {
    this.authS.logout();
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }
}

