import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Cart } from './pages/cart/cart';
import { Auth } from './pages/auth/auth';
import { User } from './pages/user/user';
import { Admin } from './pages/admin/admin';
import { Offerts } from './pages/offert/offert';
import { OffertDetail } from './pages/offert-detail/offert-detail';
import { Reservation } from './pages/reservation/reservation';

export const routes: Routes = [
    { path: '', component: Home },
  { path: 'offert/:id', component: OffertDetail }, 
  { path: 'offert', component: Offerts },
    { path: 'reservation/:id', component: Reservation }, 
  { path: 'cart', component: Cart },
  { path: 'auth', component: Auth },
  { path: 'user', component: User },
  { path: 'admin', component: Admin },
  { path: '**', redirectTo: '' }
   
    
    
];
