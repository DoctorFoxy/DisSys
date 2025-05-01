import { Component, computed, inject } from "@angular/core";
import { UserService } from "../services/user.service";
import { OrderService } from "../services/order.service";
import { MatCardModule } from "@angular/material/card";

@Component({
    templateUrl: './myorders.component.html',
    imports: [
        MatCardModule,
    ],
})
export class MyOrdersComponent {
    private userService = inject(UserService);
    private orderService = inject(OrderService);

    isLoggedIn = this.userService.isLoggedIn;
    orders = this.orderService.orders;
}