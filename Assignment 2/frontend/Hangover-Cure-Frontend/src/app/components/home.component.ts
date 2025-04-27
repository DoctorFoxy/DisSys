import { Component, inject } from "@angular/core";
import { ItemService } from "../services/item.service";
import { ItemComponent } from "./item.component";

@Component({
    templateUrl: './home.component.html',
    imports: [
        ItemComponent
    ],
})
export class HomeComponent {
    private itemService = inject(ItemService);
    items = this.itemService.fetchItems();
}