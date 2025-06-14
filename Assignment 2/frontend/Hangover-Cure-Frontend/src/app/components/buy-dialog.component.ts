import { Component, inject, model } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { Item } from "../models/item.model";
import {FormsModule} from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';

@Component({
  templateUrl: 'buy-dialog.component.html',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
})
export class BuyDialogComponent {
  readonly dialogRef = inject(MatDialogRef<BuyDialogComponent>);
  readonly item = inject<Item>(MAT_DIALOG_DATA);
  readonly deliveryAddress = model("");

  order(): void {
    console.log("order");
  }
}