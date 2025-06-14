import { inject, Injectable, signal } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map, mergeMap, Observable, throwError, timer } from "rxjs";
import { Item } from "../models/item.model";
import { Order } from "../models/order.model";
import { environment } from "../../environments/environment";


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private http = inject(HttpClient);
    
    fetchMyOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(`${environment.apiDomain}/api/orders/user`);
    }

    fetchAllOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(`${environment.apiDomain}/api/orders`);
    }

    createOrder(deliveryAddress: string, itemId: string): Observable<Order> {
        const body = { deliveryAddress, itemId };
        return this.http.post<Order>(`${environment.apiDomain}/api/orders`, body);
    }
}