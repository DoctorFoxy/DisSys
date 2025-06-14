import { Component, computed, inject, model } from "@angular/core";
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
import { OrderService } from "../services/order.service";

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
  readonly orderService = inject(OrderService);
  readonly deliveryAddress = model("");
  readonly buyButtonDisabled = computed(() => this.deliveryAddress().length < 5);
  
  state: 'init' | 'loading' | 'order placed' | 'order failed' = 'init';

  order(): void {
    this.state = 'loading';
    this.dialogRef.disableClose = true;

    this.orderService.createOrder(this.deliveryAddress(), this.item.id).subscribe({
      next: () => { this.state = 'order placed'; this.dialogRef.disableClose = false; },
      error: () => { this.state = 'order failed'; this.dialogRef.disableClose = false; }
    })
  }
}