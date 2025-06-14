import { Component, inject, input } from "@angular/core";
import { MatCardModule } from '@angular/material/card';
import { Item } from "../models/item.model";
import { MatButtonModule } from "@angular/material/button";
import { BuyDialogComponent } from "./buy-dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { UserService } from "../services/user.service";

@Component({
    selector: 'item',
    templateUrl: './item.component.html',
    imports: [
        MatCardModule,
        MatButtonModule
    ]
})
export class ItemComponent {
    item = input.required<Item>();
    readonly dialog = inject(MatDialog);
    userService = inject(UserService)

    openBuyDialog() {
        if(this.userService.isLoggedIn()) {
            this.dialog.open(BuyDialogComponent, { data: this.item() });
        } else {
            this.userService.goToLogin();
        }
    }
}