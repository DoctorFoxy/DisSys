import { Item } from "./item.model";

export interface Order {
    id: number;
    deliveryAddress: string;
    status: string;
    item: Item;
    userid: string;
    time: string;
    supplier1Status: string;
    supplier2Status: string;
}