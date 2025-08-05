import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AutherService } from '../services/auther-service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authS = inject(AutherService);

  //  Se non c'è token o non è loggato → redirect
  if (!authS.isLoggin()) {
    router.navigate(['/home']);
    return false;
  }

  // Controllo del ruolo se richiesto
  const expectedRole = route.data['role'];
  if (expectedRole) {
    const userRoles = authS.getRole();
    if (!userRoles.includes(expectedRole)) {
      router.navigate(['/home']);
      return false;
    }
  }

  // Controllo che l'ID utente sia valido
  const userId = authS.getUserId();
  if (userId === null) {
    router.navigate(['/home']);
    return false;
  }

  return true;
};
