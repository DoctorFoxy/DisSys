import { inject, Injectable, Signal } from "@angular/core";
import { Item } from "../models/item.model";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ItemService {

    private http = inject(HttpClient);

    constructor() {
        console.log(environment.apiDomain);
    }

    fetchItems(): Observable<Item[]> {
        return this.http.get<Item[]>(`${environment.apiDomain}/api/items`);
    }
}