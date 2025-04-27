package be.kuleuven.foodrestservice.exceptions;

public class OrderCreationException extends RuntimeException {
    public OrderCreationException() {
        super("Could not create order, due to incomplete definition. (One or more of the fields is empty/null)");
    }
}
