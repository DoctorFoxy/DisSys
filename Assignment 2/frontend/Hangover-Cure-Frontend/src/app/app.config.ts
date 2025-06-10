import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { authHttpInterceptorFn, provideAuth0 } from '@auth0/auth0-angular';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),

    provideRouter(routes),

    provideAuth0({
      domain: 'dev-6vt2svtrmb7h7xy2.us.auth0.com',
      clientId: 'R7ebcX3OHzCwNNvhFfVQjZbG6EhrKpET',
      authorizationParams: {
        redirect_uri: window.location.origin,
        audience: 'Backend',
        scope: 'openid profile read:orders'
      },
      httpInterceptor: {
        allowedList: [
          {
            // Allow un-authenticated calls to /api/items
            uri: 'http://localhost:8080/api/items',
            allowAnonymous: true
          },

          // Attach auth token to all other API calls
          'http://localhost:8080/api/*',
        ]
      }
    }),

    provideHttpClient(withInterceptors([authHttpInterceptorFn])),
  ]
};
