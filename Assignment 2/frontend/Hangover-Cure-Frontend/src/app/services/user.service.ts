import { computed, inject, Injectable, signal } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { AuthService } from "@auth0/auth0-angular";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private auth0Service = inject(AuthService);

    readonly loggedInUser = toSignal(this.auth0Service.user$, { initialValue: null });
    readonly isLoggedIn = toSignal(this.auth0Service.isAuthenticated$, { initialValue: false });

    // This is stupid hard because the auth0 library does NOT give access to the user's permissions/scopes
    // I think it would require parsing the access token directly and looking into that which is something that
    // a client should not do, as they are opaque. Another option would be to add a manager role into the identity token,
    // but good luck trying to figure that out with Auth0
    // readonly isManager = signal(false);

    goToLogin() {
        this.auth0Service.loginWithRedirect();
    }

    logout() {
        this.auth0Service.logout({ logoutParams: { returnTo: window.location.origin } });
    }

}