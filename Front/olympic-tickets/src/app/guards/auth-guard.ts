import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AutherService } from '../services/auther-service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authS = inject(AutherService);

  if (!authS.isLoggin()) {
    router.navigate(['/home']);
    return false;
  }

  const expectedRole = route.data['role'];
  if (expectedRole) {
    const userRoles = authS.getRole();
    if (!userRoles.includes(expectedRole)) {
      router.navigate(['/home']);
      return false;
    }
  }

  const userId = authS.getUserId();
  if (userId === null) {
    router.navigate(['/home']);
    return false;
  }

  return true;
};
