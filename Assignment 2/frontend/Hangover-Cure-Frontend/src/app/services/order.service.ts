import { inject, Injectable, signal } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map, mergeMap, Observable, throwError, timer } from "rxjs";
import { Item } from "../models/item.model";
import { Order } from "../models/order.model";


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private http = inject(HttpClient);
    
    fetchMyOrders(): Observable<Order[]> {
        return this.http.get<Order[]>('http://localhost:8080/api/orders/user');
    }

    fetchAllOrders(): Observable<Order[]> {
        return this.http.get<Order[]>('http://localhost:8080/api/orders');
    }

    createOrder(deliveryAddress: string, itemId: string): Observable<Order> {
        const body = { deliveryAddress, itemId };
        return this.http.post<Order>('http://localhost:8080/api/orders', body);
    }
}