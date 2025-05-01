import { Component, computed, inject } from "@angular/core";
import { UserService } from "../services/user.service";
import { OrderService } from "../services/order.service";
import { MatCardModule } from "@angular/material/card";

@Component({
    templateUrl: './allorders.component.html',
    imports: [
        MatCardModule,
    ],
})
export class AllOrdersComponent {
    private userService = inject(UserService);
    private orderService = inject(OrderService);

    isLoggedIn = this.userService.isLoggedIn;
    isAdmin = this.userService.isAdmin;
    orders = this.orderService.allOrders;
}