import { Component, computed, inject } from "@angular/core";
import { UserService } from "../services/user.service";
import { OrderService } from "../services/order.service";
import { MatCardModule } from "@angular/material/card";
import { DatePipe } from "@angular/common";
import { Order } from "../models/order.model";

@Component({
    templateUrl: './allorders.component.html',
    imports: [
        DatePipe,
        MatCardModule,
    ],
})
export class AllOrdersComponent {
    private userService = inject(UserService);
    private orderService = inject(OrderService);

    isLoggedIn = this.userService.isLoggedIn;
    isLoading = true;
    isError = false;
    orders: Order[] = [];

    ngOnInit() {
        this.orderService.fetchAllOrders().subscribe({
            next: (orders) => {
                this.isLoading = false;
                this.orders = orders;
            },
            error: () => {
                this.isLoading = false;
                this.isError = true;
            }
        })
    }
}