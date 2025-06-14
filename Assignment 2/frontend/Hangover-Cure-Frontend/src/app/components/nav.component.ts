import { Component, inject } from "@angular/core";
import { RouterLink } from "@angular/router";
import { UserService } from "../services/user.service";

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html',
    imports: [
        RouterLink
    ],
})
export class NavComponent {
    private userService = inject(UserService);
    isLoggedIn = this.userService.isLoggedIn;
}