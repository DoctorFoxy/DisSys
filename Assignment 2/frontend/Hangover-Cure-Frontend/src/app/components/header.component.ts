import { Component, computed, inject } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { UserService } from "../services/user.service";
import { MatButtonModule } from "@angular/material/button";
import { MatDialog } from "@angular/material/dialog";
import { LoginDialogComponent } from "./login-dialog.component";
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
    user = this.userService.loggedInUser;
    isLoggedIn = this.userService.isLoggedIn;

    private dialog = inject(MatDialog);

    openLoginDialog() {
        this.dialog.open(LoginDialogComponent);
    }

    logout() {
        this.userService.logout();
    }
}