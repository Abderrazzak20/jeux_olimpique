import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, HttpInterceptor, withInterceptors } from '@angular/common/http';
import { TokenInterceptor } from './tokenInterceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
        (req, next) => {
          const token = localStorage.getItem('token');
          if (token) {
            const cloned = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${token}`)
            });
            return next(cloned);
          }
          return next(req);
        }
      ])
    )


  ]
};
