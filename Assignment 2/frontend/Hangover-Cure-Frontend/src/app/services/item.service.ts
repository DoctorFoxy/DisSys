import { inject, Injectable, Signal } from "@angular/core";
import { Item } from "../models/item.model";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ItemService {

    private http = inject(HttpClient);

    fetchItems(): Observable<Item[]> {
        return this.http.get<Item[]>('http://localhost:8080/api/items');
    }
}