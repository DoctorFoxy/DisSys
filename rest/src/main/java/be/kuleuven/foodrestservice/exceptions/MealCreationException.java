package be.kuleuven.foodrestservice.exceptions;

public class MealCreationException extends RuntimeException {
    public MealCreationException() {
        super("Could not create meal, due to incomplete definition. (One or more of the fields is empty/null)");
    }
}
