import { Routes,RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { Home } from './pages/home/home';
import { Offerts } from './pages/offerts/offerts';
import { Cart } from './pages/cart/cart';
import { Auth } from './pages/auth/auth';
import { User } from './pages/user/user';
import { Admin } from './pages/admin/admin';

export const routes: Routes = [
{path:"",component:Home},
{path:"offerts",component:Offerts},
{path:"cart",component:Cart},
{path:"auth",component:Auth},
{path:"user",component:User},
{path:"admin",component:Admin},
{path:"**",redirectTo:""}

];
@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class AppRoutingModule {}