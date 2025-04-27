import { Component, computed, inject, model, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from "@angular/material/input";
import { UserService } from "../services/user.service";

@Component({
    templateUrl: './login-dialog.component.html',
    imports: [
        MatFormFieldModule,
        MatInputModule,
        FormsModule,
        MatButtonModule,
        MatDialogTitle,
        MatDialogContent,
        MatDialogActions
    ]
})
export class LoginDialogComponent {
    private userService = inject(UserService);
    readonly dialogRef = inject(MatDialogRef<LoginDialogComponent>);
    readonly loginname = model('');
    readonly loginButtonDisabled = computed(() => !this.loginname());

    close() {
        this.dialogRef.close();
    }

    login() {
        const loginname = this.loginname();
        const success = this.userService.login(loginname);
        if(success) {
            this.close();
        }
        else {
            alert('login failed');
        }
    }
}