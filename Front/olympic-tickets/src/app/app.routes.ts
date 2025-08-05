import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Cart } from './pages/cart/cart';
import { Auth } from './pages/auth/auth';
import { User } from './pages/user/user';
import { Admin } from './pages/admin/admin';
import { Offerts } from './pages/offert/offert';
import { OffertDetail } from './pages/offert-detail/offert-detail';
import { Reservation } from './pages/reservation/reservation';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'offert/:id', component: OffertDetail,canActivate: [authGuard]  },
  { path: 'offert', component: Offerts,canActivate: [authGuard]  },
  { path: 'reservation/:id', component: Reservation,canActivate: [authGuard]  },
  { path: 'cart', component: Cart,canActivate: [authGuard]  },
  { path: 'auth', component: Auth },
  { path: 'user', component: User },
  { path: 'admin', component: Admin },

  { path: '**', redirectTo: '' }
  



];
