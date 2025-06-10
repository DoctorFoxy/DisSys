import { inject, Injectable, signal } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
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
}