import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { authHttpInterceptorFn, provideAuth0 } from '@auth0/auth0-angular';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { environment } from '../environments/environment';

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
        scope: 'openid profile read:orders',
      },
      httpInterceptor: {
        allowedList: [
          {
            // Allow un-authenticated calls to /api/items
            uri: `${environment.apiDomain}/api/items`,
            allowAnonymous: true
          },

          {
            // Allow un-authenticated calls to /api/authdebug/public
            uri: `${environment.apiDomain}/api/authdebug/public`,
            allowAnonymous: true
          },

          // Attach auth token to all other API calls
          `${environment.apiDomain}/api/*` ,
        ]
      }
    }),

    provideHttpClient(withInterceptors([authHttpInterceptorFn])),
  ]
};
