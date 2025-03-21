package be.kuleuven.foodrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MealCreationAdvice {

    @ResponseBody
    @ExceptionHandler(MealCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String mealCreationExceptionHandler(MealCreationException ex) {
        return ex.getMessage();
    }
}
