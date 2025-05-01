export enum OrderStatus {
    Ongoing,
    Succeeded,
    Failed,
}

export interface Order {
    id: number;
    itemid: number;
    userid: number;
    deliveryAddress: string;
    status: OrderStatus;
    time: Date;
}