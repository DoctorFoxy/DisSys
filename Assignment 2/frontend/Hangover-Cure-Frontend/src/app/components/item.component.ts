import { Component, input } from "@angular/core";
import { MatCardModule } from '@angular/material/card';
import { Item } from "../models/item.model";

@Component({
    selector: 'item',
    templateUrl: './item.component.html',
    imports: [
        MatCardModule
    ]
})
export class ItemComponent {
    item = input.required<Item>();
}