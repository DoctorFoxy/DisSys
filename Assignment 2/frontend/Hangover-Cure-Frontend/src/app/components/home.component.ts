import { Component, inject } from "@angular/core";
import { ItemService } from "../services/item.service";
import { ItemComponent } from "./item.component";
import { Observable } from "rxjs";
import { Item } from "../models/item.model";
import { AsyncPipe } from "@angular/common";
import { AuthdebugComponent } from "./authdebug.component";

@Component({
    templateUrl: './home.component.html',
    imports: [
    AsyncPipe,
    ItemComponent,
    AuthdebugComponent
],
})
export class HomeComponent {
    private itemService = inject(ItemService);
    items$: Observable<Item[]> = this.itemService.fetchItems();
}