import { computed, inject, Injectable, signal } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { AuthService } from "@auth0/auth0-angular";
import { map } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private auth0Service = inject(AuthService);

    readonly loggedInUser = toSignal(this.auth0Service.user$, { initialValue: null });
    readonly isLoggedIn = toSignal(this.auth0Service.isAuthenticated$, { initialValue: false });
    readonly isAdmin = signal(false);

    constructor() {
        this.auth0Service.idTokenClaims$.subscribe((tokens) => {
            console.log('NEW TOKEN: ', tokens);
        })
    }

    goToLogin() {
        this.auth0Service.loginWithRedirect();
    }

    logout() {
        this.auth0Service.logout();
    }

}