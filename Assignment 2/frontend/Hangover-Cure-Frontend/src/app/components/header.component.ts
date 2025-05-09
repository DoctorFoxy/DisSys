import { Component, computed, inject } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { UserService } from "../services/user.service";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatTooltipModule } from "@angular/material/tooltip";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    imports: [
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatTooltipModule
    ]
})
export class HeaderComponent {
    private userService = inject(UserService);
    readonly isLoggedIn = this.userService.isLoggedIn;
    readonly user = this.userService.loggedInUser;

    goToLogin() {
        this.userService.goToLogin();
    }

    logout() {
        this.userService.logout();
    }
}