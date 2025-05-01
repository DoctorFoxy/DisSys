import { Component, computed, inject } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { UserService } from "../services/user.service";
import { MatButtonModule } from "@angular/material/button";
import { MatDialog } from "@angular/material/dialog";
import { LoginDialogComponent } from "./login-dialog.component";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    imports: [
        MatToolbarModule,
        MatButtonModule
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
}