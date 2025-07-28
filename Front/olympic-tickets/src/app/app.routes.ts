import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Cart } from './pages/cart/cart';
import { Auth } from './pages/auth/auth';
import { User } from './pages/user/user';
import { Admin } from './pages/admin/admin';
import { Offerts } from './pages/offert/offert';

export const routes: Routes = [
    {path:"",component:Home},
    {path:"offert",component:Offerts},
    {path:"cart",component:Cart},
    {path:"auth",component:Auth},
    {path:"user",component:User},
    {path:"admin",component:Admin},
    {path:"**",redirectTo:""}
    
    
];