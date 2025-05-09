import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAuth0 } from '@auth0/auth0-angular';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAuth0({
      domain: 'dev-6vt2svtrmb7h7xy2.us.auth0.com',
      clientId: 'p1aZPua8EnW0m4VzpfbZkNGvE0FDVMAJ',
      authorizationParams: {
        redirect_uri: window.location.origin
      }
    }),
  ]
};
