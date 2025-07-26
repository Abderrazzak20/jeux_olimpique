import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Offert } from './pages/offert/offert';
import { Cart } from './pages/cart/cart';
import { Auth } from './pages/auth/auth';
import { User } from './pages/user/user';
import { Admin } from './pages/admin/admin';

export const routes: Routes = [
    {path:"",component:Home},
    {path:"offert",component:Offert},
    {path:"cart",component:Cart},
    {path:"auth",component:Auth},
    {path:"user",component:User},
    {path:"admin",component:Admin},
    {path:"**",redirectTo:""}
    
    
];