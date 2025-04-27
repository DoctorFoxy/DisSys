import { Injectable, signal } from "@angular/core";

export interface User {
    loginname: string;
    displayname: string;
    isAdmin: boolean;
}

const users: User[] = [
    {
        displayname: 'Manager',
        loginname: 'manager',
        isAdmin: true,
    },
    {
        displayname: 'Normal Guy',
        loginname: 'normal',
        isAdmin: false,
    },
    {
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

    login(loginname: string): boolean {
        const user = users.find(u => u.loginname == loginname);
        if (user) {
            this.loggedInUserSignal.set(user);
            return true;
        }
        return false;
    }

}