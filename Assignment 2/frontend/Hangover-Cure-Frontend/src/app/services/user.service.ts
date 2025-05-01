import { computed, Injectable, signal } from "@angular/core";
import { User } from "../models/user.model";

const users: User[] = [
    {
        id: 0,
        displayname: 'Manager',
        loginname: 'manager',
        isAdmin: true,
    },
    {
        id: 1,
        displayname: 'Normal Guy',
        loginname: 'normal',
        isAdmin: false,
    },
    {
        id: 2,
        displayname: 'Heavy Drinker',
        loginname: 'heavy',
        isAdmin: false,
    }
]


@Injectable({
    providedIn: 'root'
})
export class UserService {
    private loggedInUserSignal = signal<User | null>(null);
    readonly loggedInUser = this.loggedInUserSignal.asReadonly();
    readonly isLoggedIn = computed(() => !!this.loggedInUser());
    readonly isAdmin = computed(() => this.loggedInUser()?.isAdmin || false);

    login(loginname: string): boolean {
        const user = users.find(u => u.loginname == loginname);
        if (user) {
            this.loggedInUserSignal.set(user);
            return true;
        }
        return false;
    }

    logout() {
        this.loggedInUserSignal.set(null);
    }

}