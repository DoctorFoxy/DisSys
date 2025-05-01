import { computed, inject, Injectable, signal } from "@angular/core";
import { UserService } from "./user.service";
import { Order, OrderStatus } from "../models/order.model";


const orders: {[userId: number]: Order[]} = {
    // Manager
    0: [ ],
    // Normal Guy
    1: [
        {
            id: 1,
            itemid: 0,
            userid: 1,
            deliveryAddress: 'Mr. Normal, Naamsestraat 12, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 3, 12, 9, 24, 32),
        },
        {
            id: 2,
            itemid: 0,
            userid: 1,
            deliveryAddress: 'Mr. Normal, Naamsestraat 12, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 4, 1, 12, 10, 32),
        },
    ],
    // Heavy Guy
    2: [
        {
            id: 3,
            itemid: 2,
            userid: 2,
            deliveryAddress: 'Mr. Heavy, Oude Markt 13, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 1, 12, 9, 24, 32),
        },
        {
            id: 4,
            itemid: 3,
            userid: 2,
            deliveryAddress: 'Mr. Heavy, Oude Markt 13, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 2, 1, 7, 10, 32),
        },
        {
            id: 5,
            itemid: 4,
            userid: 2,
            deliveryAddress: 'Mr. Heavy, Oude Markt 13, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 2, 7, 13, 55, 32),
        },
        {
            id: 6,
            itemid: 5,
            userid: 2,
            deliveryAddress: 'Mr. Heavy, Oude Markt 13, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 2, 14, 12, 17, 32),
        },
        {
            id: 7,
            itemid: 6,
            userid: 2,
            deliveryAddress: 'Mr. Heavy, Oude Markt 13, 3000 Leuven',
            status: OrderStatus.Succeeded,
            time: new Date(2025, 3, 1, 15, 1, 32),
        },
    ]
}


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private userService = inject(UserService);
    readonly orders = computed(() => {
        const user = this.userService.loggedInUser();
        if(user) {
            return orders[user.id] ?? [];
        }
        else {
            return null;
        }
    });
}