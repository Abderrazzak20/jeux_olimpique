import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Cart } from './pages/cart/cart';
import { Offerts } from './pages/offert/offert';
import { OffertDetail } from './pages/offert-detail/offert-detail';
import { Reservation } from './pages/reservation/reservation';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { authGuard } from './guards/auth-guard';
import { OffertCreate } from './pages/offert-create/offert-create';
import { OffertEdit } from './pages/offert-edit/offert-edit';
import { OffertSales } from './pages/offert-sales/offert-sales';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'offert', component: Offerts, canActivate: [authGuard] },
  { path: 'offert/ventes', component: OffertSales, canActivate: [authGuard] },
  { path: 'offert/create', component: OffertCreate, canActivate: [authGuard] },
  { path: 'offert/edit/:id', component: OffertEdit, canActivate: [authGuard] },
  { path: 'offert/:id', component: OffertDetail, canActivate: [authGuard] },
  { path: 'reservation', component: Reservation, canActivate: [authGuard] },
  { path: 'cart', component: Cart, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
];
